package com.example.demo.utils

import com.example.demo.infrastructure.utils.Log
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Quran line boundary matcher with 3 diagnostic phases:
 *  1) Word-level multi-pass anchors (STRICT/BALANCED/TOLERANT)
 *  2) Word-level with relaxed token merge (fixes OCR tiny splits)
 *  3) Character-level fallback (no spaces), sliding-window Levenshtein
 *
 * The goal here is *diagnostics*: everything logs via Log.info().
 */
object QuranLineBoundaryMatcher {

    // --------------------------- Public API ---------------------------

    fun extractCleanLineOrEmpty(actualText: String, rawText: String): String {
        val r = findBestBoundary(actualText, rawText)
        return r.correctLine ?: ""
    }

    fun findBestBoundary(actualText: String, rawText: String): Result {
        Log.info("========= QuranLineBoundaryMatcher: START =========")
        Log.info("Actual Text: $actualText")
        Log.info("Raw Text   : $rawText")

        // ----- PHASE 1 -----
        Log.info("\n===== PHASE 1: NORMAL WORD MATCH =====")
        val p1 = multiPassWordMatch(actualText, rawText, passLabelPrefix = "P1")
        if (p1.correctLine != null) {
            Log.info("🎯 PHASE 1 ACCEPTED → ${p1.passLabel} | conf=${fmt(p1.confidence)}")
            Log.info("Extracted: ${p1.correctLine}")
            Log.info("==================================================")
            return p1
        }
        Log.info("❌ PHASE 1 produced no acceptable candidate.")

        // ----- PHASE 2 -----
        Log.info("\n===== PHASE 2: RELAXED TOKEN MERGE (WORD-LEVEL) =====")
        val relaxedRaw = relaxedMergeTokens(rawText)
        val relaxedActual = relaxedMergeTokens(actualText)
        Log.info("RELAXED raw : $relaxedRaw")
        Log.info("RELAXED actual: $relaxedActual")

        val p2 = multiPassWordMatch(relaxedActual, relaxedRaw, passLabelPrefix = "P2")
        if (p2.correctLine != null) {
            Log.info("🎯 PHASE 2 ACCEPTED → ${p2.passLabel} | conf=${fmt(p2.confidence)}")
            Log.info("Extracted: ${p2.correctLine}")
            Log.info("==================================================")
            return p2
        }
        Log.info("❌ PHASE 2 produced no acceptable candidate.")

        // ----- PHASE 3 -----
        Log.info("\n===== PHASE 3: CHARACTER-LEVEL FALLBACK (NO SPACES) =====")
        val p3 = charLevelFallback(actualText, rawText)
        if (p3.correctLine != null) {
            Log.info("🎯 PHASE 3 ACCEPTED | conf=${fmt(p3.confidence)}")
            Log.info("Extracted: ${p3.correctLine}")
            Log.info("==================================================")
            return p3
        }
        Log.info("❌ PHASE 3 failed to find any acceptable window.")
        Log.info("========= QuranLineBoundaryMatcher: END (FAILED) =========")
        return Result.empty()
    }

    // --------------------------- Data Models ---------------------------

    data class Thresholds(
        val pairwise: Double,
        val whole: Double,
        val anchorMatchCount: Int,
        val label: String
    )

    data class Result(
        val startIdx: Int?,          // word index (for word phases) or char idx (for char phase)
        val endIdx: Int?,
        val correctLine: String?,
        val confidence: Double,
        val passLabel: String = ""
    ) {
        companion object {
            fun empty() = Result(null, null, null, 0.0, "")
        }
    }

    // --------------------------- PHASE 1 / PHASE 2 (shared) ---------------------------

    private fun multiPassWordMatch(actualText: String, rawText: String, passLabelPrefix: String): Result {
        val passes = listOf(
            Thresholds(pairwise = 95.0, whole = 90.0, anchorMatchCount = 4, label = "$passLabelPrefix-STRICT"),
            Thresholds(pairwise = 90.0, whole = 75.0, anchorMatchCount = 3, label = "$passLabelPrefix-BALANCED"),
            Thresholds(pairwise = 80.0, whole = 60.0, anchorMatchCount = 2, label = "$passLabelPrefix-TOLERANT")
        )
        val candidates = mutableListOf<Result>()

        for ((idx, th) in passes.withIndex()) {
            Log.info("\n--- ${th.label} (pass ${idx + 1}) --- pairwise≥${th.pairwise} whole≥${th.whole} anchors≥${th.anchorMatchCount}/4")
            val r = findLineBoundaryWithThresholds(actualText, rawText, th)
            if (r.correctLine != null) {
                Log.info("✅ ${th.label} produced candidate: start=${r.startIdx}, end=${r.endIdx}, conf=${fmt(r.confidence)}")
                candidates += r
                if (r.confidence >= 95.0) {
                    Log.info("🎯 Early stop: ${th.label} candidate conf≥95")
                    break
                }
            } else {
                Log.info("… ${th.label} → no candidate.")
            }
        }
        val best = candidates.maxByOrNull { it.confidence }
        if (best == null) {
            Log.info("No candidates across ${passes.size} passes.")
            return Result.empty()
        }
        Log.info("Best over passes: ${best.passLabel}, conf=${fmt(best.confidence)}")
        return best
    }

    private fun findLineBoundaryWithThresholds(actualText: String, rawText: String, th: Thresholds): Result {
        val actualOrig = tokenize(actualText)
        val rawOrig = tokenize(rawText)
        Log.info("Tokenized actual (${actualOrig.size}): $actualOrig")
        Log.info("Tokenized raw    (${rawOrig.size}): $rawOrig")

        if (actualOrig.isEmpty() || rawOrig.isEmpty()) return Result.empty()

        val actualNorm = actualOrig.map { normalize(it).also { n -> Log.info(" normalize(A) '$it' → '$n'") } }
        val rawNorm0   = rawOrig.map { normalize(it).also { n -> Log.info(" normalize(R) '$it' → '$n'") } }
        val rawNorm    = collapseDuplicates(rawNorm0)

        if (rawNorm != rawNorm0) {
            Log.info("🔧 Collapsed duplicates in raw: $rawNorm0 → $rawNorm")
        }

        val start = findAnchor(actualNorm, rawNorm, wantStart = true, anchorMatchCount = th.anchorMatchCount)
        if (start == -1) {
            Log.info("❌ Start anchor not found in ${th.label}")
            return Result.empty()
        }
        Log.info("✅ Start anchor at $start (actual[${start}..${start + min(3, actualNorm.size - 1)}])")

        val expectedEnd = start + rawNorm.size - 1
        val endWindow = when {
            rawNorm.size >= 12 -> 4
            rawNorm.size >= 7  -> 3
            else               -> 2
        }
        val end = findAnchor(actualNorm, rawNorm, wantStart = false, anchorMatchCount = th.anchorMatchCount, expectedEnd = expectedEnd, startIdx = start, window = endWindow)
        if (end == -1 || end < start) {
            Log.info("❌ End anchor not found in ${th.label}")
            return Result.empty()
        }
        Log.info("✅ End anchor at $end (actual[${max(0, end - 3)}..$end])")

        // Extract candidate segment from ORIGINAL (to preserve diacritics, spacing)
        val segment = actualOrig.subList(start, end + 1).joinToString(" ")
        Log.info("🔎 Candidate segment: $segment")

        // Verify anchors and similarities
        val anchorsOK = verifyAnchors(actualNorm, rawNorm, start, end, th.anchorMatchCount)
        val pairwise  = pairwiseSimilarity(actualNorm.subList(start, end + 1), rawNorm)
        val whole     = wholeSimilarity(normalize(segment), normalize(rawOrig.joinToString(" ")))

        Log.info("Anchors OK? $anchorsOK  | Pairwise=${fmt(pairwise)} (need≥${th.pairwise}) | Whole=${fmt(whole)} (need≥${th.whole})")

        val accepted = anchorsOK && pairwise >= th.pairwise && whole >= th.whole
        if (!accepted) {
            Log.info("❌ ${th.label} rejected: below thresholds.")
            return Result.empty()
        }

        val confidence = 0.6 * pairwise + 0.4 * whole
        return Result(start, end, segment, confidence, th.label)
    }

    private fun findAnchor(
        actualNorm: List<String>,
        rawNorm: List<String>,
        wantStart: Boolean,
        anchorMatchCount: Int,
        expectedEnd: Int = -1,
        startIdx: Int = 0,
        window: Int = 3
    ): Int {
        val label = if (wantStart) "Start" else "End"
        val target = if (wantStart) rawNorm.take(4) else rawNorm.takeLast(4)

        val aRange: IntRange = if (wantStart) {
            if (actualNorm.size < 4) 0..(actualNorm.size - 1) else 0..(actualNorm.size - 1)
        } else {
            val lo = max(startIdx + 1, expectedEnd - window)
            val hi = min(actualNorm.size - 1, expectedEnd + window)
            lo..hi
        }

        var bestIdx = -1
        var bestCount = -1

        for (i in aRange) {
            val segment = if (wantStart) {
                val end = min(actualNorm.size, i + 4)
                actualNorm.subList(i, end)
            } else {
                val s = max(startIdx + 1, i - 3)
                actualNorm.subList(s, i + 1)
            }
            val count = countWordMatches(segment, target)
            Log.info("$label-try i=$i | segment=${segment} vs target=${target} | matchCount=$count/4 (best=$bestCount)")
            if (count > bestCount) {
                bestCount = count
                bestIdx = i
            }
            if (count >= anchorMatchCount) {
                Log.info("$label anchor locked at i=$i (count=$count)")
                return i
            }
        }
        Log.info("$label anchor best fallback i=$bestIdx (count=$bestCount) — need≥$anchorMatchCount")
        return if (bestCount >= anchorMatchCount) bestIdx else -1
    }

    private fun verifyAnchors(actualNorm: List<String>, rawNorm: List<String>, s: Int, e: Int, need: Int): Boolean {
        val firsts = actualNorm.subList(s, min(s + 4, actualNorm.size))
        val lasts  = actualNorm.subList(max(0, e - 3), e + 1)
        val rf = rawNorm.take(4)
        val rl = rawNorm.takeLast(4)
        val startOk = countWordMatches(firsts, rf) >= need
        val endOk   = countWordMatches(lasts, rl) >= need
        Log.info("Verify anchors → startOk=$startOk endOk=$endOk | need≥$need")
        return startOk && endOk
    }

    private fun countWordMatches(a: List<String>, b: List<String>): Int {
        var c = 0
        for (i in 0 until min(a.size, b.size)) {
            val s = wordSim(a[i], b[i])
            Log.info("  • wordSim('${a[i]}','${b[i]}')=${fmt(s)}")
            if (s >= 70.0) c++
        }
        return c
    }

    private fun pairwiseSimilarity(a: List<String>, r: List<String>): Double {
        var i = 0; var j = 0
        var usedSkipA = false; var usedSkipR = false
        var total = 0.0; var pairs = 0
        Log.info("→ pairwiseSimilarity on windows a=${a.size} r=${r.size}")

        while (i < a.size && j < r.size) {
            val s = wordSim(a[i], r[j])
            Log.info("   pair a[$i]='${a[i]}' ~ r[$j]='${r[j]}' → ${fmt(s)}")
            if (s >= 60.0) {
                total += s; pairs++; i++; j++
            } else {
                val sa = if (!usedSkipA && i + 1 < a.size) wordSim(a[i + 1], r[j]) else -1.0
                val sr = if (!usedSkipR && j + 1 < r.size) wordSim(a[i], r[j + 1]) else -1.0
                Log.info("     trySkipA=${fmt(sa)} trySkipR=${fmt(sr)} (usedA=$usedSkipA usedR=$usedSkipR)")
                when {
                    sa >= 60 && sa >= sr -> { usedSkipA = true; i++ ; Log.info("     🔀 skip A → i=$i,j=$j") }
                    sr >= 60 -> { usedSkipR = true; j++ ; Log.info("     🔀 skip R → i=$i,j=$j") }
                    else -> { i++; j++; Log.info("     ↘ advance both → i=$i,j=$j") }
                }
            }
        }
        val avg = if (pairs == 0) 0.0 else total / pairs
        Log.info("← pairwise avg=${fmt(avg)} from $pairs pairs (sum=${fmt(total)})")
        return avg
    }

    // --------------------------- PHASE 2 helpers ---------------------------

    /**
     * Try to undo OCR splits by merging tiny tokens (length 1–2) with neighbors.
     * Heuristics:
     *  - join single-char token to previous if previous is Arabic and > 1 char
     *  - otherwise join to next
     * All ops logged.
     */
    private fun relaxedMergeTokens(text: String): String {
        val tokens = tokenize(text)
        Log.info("RELAXED MERGE: original tokens (${tokens.size}): $tokens")
        if (tokens.isEmpty()) return text

        val out = mutableListOf<String>()
        var i = 0
        while (i < tokens.size) {
            val t = tokens[i]
            val n = normalize(t)
            if (n.length <= 2 && isArabic(n)) {
                // Try to merge to previous
                if (out.isNotEmpty() && isArabic(normalize(out.last()))) {
                    val merged = out.last() + t
                    Log.info("  🔧 merge tiny '${t}' → prev: '${out.last()}' + '${t}' = '$merged'")
                    out[out.lastIndex] = merged
                } else if (i + 1 < tokens.size) {
                    val merged = t + tokens[i + 1]
                    Log.info("  🔧 merge tiny '${t}' → next: '${t}' + '${tokens[i + 1]}' = '$merged'")
                    out += merged
                    i++ // skip the next because we merged it
                } else {
                    out += t
                }
            } else {
                out += t
            }
            i++
        }
        Log.info("RELAXED MERGE: result tokens (${out.size}): $out")
        return out.joinToString(" ")
    }

    // --------------------------- PHASE 3 (character-level) ---------------------------

    private fun charLevelFallback(actualText: String, rawText: String): Result {
        Log.info("PHASE 3: Build no-space normalized strings and mapping...")
        val (aNorm, aMap) = normalizeNoSpaceWithMap(actualText)
        val (rNorm, _)    = normalizeNoSpaceWithMap(rawText)
        Log.info(" aNorm len=${aNorm.length} | rNorm len=${rNorm.length}")
        Log.info(" aNorm='$aNorm'")
        Log.info(" rNorm='$rNorm'")

        if (aNorm.isEmpty() || rNorm.isEmpty()) {
            Log.info("PHASE 3 aborted: empty normalized strings.")
            return Result.empty()
        }

        // Sliding a window around rNorm length ± 10% (min 0, max aNorm length)
        val baseLen = rNorm.length
        val minL = max(1, (baseLen * 0.90).roundToInt())
        val maxL = min(aNorm.length, (baseLen * 1.10).roundToInt())
        Log.info("PHASE 3 window lengths: $minL..$maxL")

        var bestSim = -1.0
        var bestRange: IntRange? = null
        var bestWinLen = -1

        for (winLen in minL..maxL) {
            if (winLen > aNorm.length) break
            val lastStart = aNorm.length - winLen
            Log.info("— Try window length=$winLen (lastStart=$lastStart)")
            for (start in 0..lastStart) {
                val sub = aNorm.substring(start, start + winLen)
                val sim = strSim(sub, rNorm)
                if (start % 50 == 0) {
                    Log.info("   • win[$start..${start + winLen}) sim=${fmt(sim)}")
                }
                if (sim > bestSim) {
                    bestSim = sim
                    bestRange = start until (start + winLen)
                    bestWinLen = winLen
                }
            }
        }

        if (bestRange == null) {
            Log.info("PHASE 3: No best window found.")
            return Result.empty()
        }

        Log.info("PHASE 3: BEST char-window len=$bestWinLen, normRange=${bestRange}, sim=${fmt(bestSim)}")
        if (bestSim < 60.0) {
            Log.info("PHASE 3 rejected: bestSim < 60.")
            return Result.empty()
        }

        // Map back to original substring
        val origStartIdx = aMap[bestRange.first]
        val origEndIdx   = aMap[bestRange.last]  // inclusive
        val safeEnd = min(actualText.length - 1, origEndIdx)
        val extracted = actualText.substring(origStartIdx, safeEnd + 1)

        // Optional: expand to nearest word boundaries (visual)
        val (finalExtract, adjustedRange) = snapToWordBoundaries(extracted, actualText, origStartIdx, safeEnd)

        Log.info("PHASE 3 extracted: '$extracted' → snapped: '$finalExtract'")

        // Confidence derived only from char-sim in phase 3
        return Result(adjustedRange.first, adjustedRange.last, finalExtract, bestSim, "P3-CHAR")
    }

    private fun snapToWordBoundaries(s: String): String {
        val trimmed = s.trim()
        // If the first/last char is Arabic letter, keep; if weird punctuation, strip
        return trimmed.trim(' ', '،', '؛', '؟', '.', ',', '…', 'ـ', '-', '«', '»', '(', ')', '[', ']')
    }

    // Build normalized (no spaces) string + mapping array from normalized idx → original char idx
    private fun normalizeNoSpaceWithMap(src: String): Pair<String, IntArray> {
        val out = StringBuilder()
        val map = ArrayList<Int>()
        for ((idx, ch) in src.withIndex()) {
            if (ch.isWhitespace()) continue
            if (isTashkeel(ch)) continue
            if (isPunct(ch)) continue
            val norm = normalizeChar(ch)
            if (norm == null) continue
            out.append(norm)
            map.add(idx)
        }
        return out.toString() to map.toIntArray()
    }

    private fun isTashkeel(ch: Char): Boolean = ch in '\u064B'..'\u0652'
    private fun isPunct(ch: Char): Boolean = "ـ\"'`^،؛؟!,:.\\-()[]{}«»".contains(ch)
    private fun normalizeChar(ch: Char): Char? {
        return when (ch) {
            'أ','إ','آ' -> 'ا'
            'ؤ' -> 'و'
            'ئ' -> 'ي'
            'ة' -> 'ه'
            'ى' -> 'ي'
            else -> ch
        }
    }

    // --------------------------- Utilities ---------------------------

    private fun tokenize(t: String): List<String> =
        t.split(Regex("\\s+")).filter { it.isNotBlank() }

    private fun normalize(w: String): String {
        val c = w
            .replace(Regex("[\\u064B-\\u0652]"), "")   // strip tashkeel
            .replace("أ", "ا").replace("إ", "ا").replace("آ", "ا")
            .replace("ؤ", "و").replace("ئ", "ي").replace("ة", "ه").replace("ى", "ي")
            .replace("ۥ", "")
            .replace(Regex("[ـ\"'`^،؛؟!,:.\\-\\(\\)\\[\\]{}]"), "")
            .replace(Regex("[^\\p{InArabic}\\s]"), "")
            .trim()
        return c
    }

    private fun collapseDuplicates(words: List<String>): List<String> {
        if (words.isEmpty()) return words
        val out = ArrayList<String>(words.size)
        var last: String? = null
        for (w in words) {
            if (w != last) out.add(w)
            last = w
        }
        return out
    }

    private fun isArabic(s: String): Boolean = s.any { it in '\u0600'..'\u06FF' }

    // Similarities
    private fun wordSim(a: String, b: String): Double = strSim(a, b)

    private fun strSim(a: String, b: String): Double {
        if (a.isEmpty() && b.isEmpty()) return 100.0
        if (a.isEmpty() || b.isEmpty()) return 0.0
        val d = levenshtein(a, b)
        return (1.0 - d.toDouble() / max(a.length, b.length)) * 100.0
    }

    private fun wholeSimilarity(a: String, b: String): Double = strSim(a, b)

    private fun levenshtein(a: String, b: String): Int {
        val n = a.length
        val m = b.length
        if (n == 0) return m
        if (m == 0) return n
        val dp = Array(n + 1) { IntArray(m + 1) }
        for (i in 0..n) dp[i][0] = i
        for (j in 0..m) dp[0][j] = j
        for (i in 1..n) {
            for (j in 1..m) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,      // deletion
                    dp[i][j - 1] + 1,      // insertion
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        return dp[n][m]
    }


    /**
     * Ensures the extracted substring starts and ends at full word boundaries.
     * It expands outward if needed until both sides are complete Arabic words.
     * Logs detailed diagnostics to help trace incomplete word correction.
     */
    private fun snapToWordBoundaries(src: String, fullText: String, startIdx: Int, endIdx: Int): Pair<String, IntRange> {
        var newStart = startIdx
        var newEnd = endIdx

        // Helper to test if a char is Arabic letter
        fun isArabicLetter(ch: Char) = ch in '\u0600'..'\u06FF'

        // --- Expand start backwards until start of word or text ---
        while (newStart > 0 && isArabicLetter(fullText[newStart - 1])) {
            Log.info("🔧 Expanding start backward: '${fullText[newStart - 1]}' before current start (idx=$newStart)")
            newStart--
        }

        // --- Expand end forwards until end of word or text ---
        while (newEnd + 1 < fullText.length && isArabicLetter(fullText[newEnd + 1])) {
            Log.info("🔧 Expanding end forward: '${fullText[newEnd + 1]}' after current end (idx=$newEnd)")
            newEnd++
        }

        // Extract the adjusted substring
        val result = fullText.substring(newStart, newEnd + 1).trim()

        Log.info("✅ snapToWordBoundaries adjusted range: [$newStart..$newEnd]")
        Log.info("✅ Extracted corrected line: '$result'")

        return result to (newStart..newEnd)
    }


    private fun fmt(v: Double) = "%.2f".format(v)
}
