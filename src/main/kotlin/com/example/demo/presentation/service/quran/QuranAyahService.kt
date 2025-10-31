package com.example.demo.presentation.service.quran

import com.example.demo.domain.repository.quran.QuranAyahRepository
import com.example.demo.domain.usecases.quran.GetQuranTextWithSurahBreaksUseCase
import com.example.demo.presentation.dto.quran.LineComparisonResult
import com.example.demo.presentation.dto.quran.QuranAyahRequestDTO
import com.example.demo.utils.QuranLineBoundaryMatcher.findBestBoundary
import org.springframework.stereotype.Service

@Service
class QuranAyahService(
    private val quranAyahRepository: QuranAyahRepository,
    private val getQuranTextWithSurahBreaksUseCase: GetQuranTextWithSurahBreaksUseCase
) {

    // Fetch Ayahs based on Surah number and Ayah range and compare with the provided raw line
    fun compareAyahsWithRawLine(request: QuranAyahRequestDTO): LineComparisonResult {
        // Fetch the correct Ayahs from the database
        val correctAyahs = quranAyahRepository.findBySurahIdAndAyahNumberInSurahBetween(
            request.surahNumber,
            request.ayahStartNumber,
            request.ayahEndNumber
        )

        // Generate the correct text for the Ayahs
        val correctText = getQuranTextWithSurahBreaksUseCase.execute(correctAyahs)

        // Extract the correct line for comparison with the raw line
        val correctLine = findBestBoundary(correctText.textArabic, request.rawText)

        // Return the comparison result with raw line and corrected line
        return LineComparisonResult(
            rawText = request.rawText,
            correctLine = correctLine.toString(),
            errors = identifyErrors(correctLine.correctLine.toString(), request.rawText)
        )
    }


    // Helper function to identify errors in the raw line compared to the correct line
    private fun identifyErrors(correctLine: String, rawLine: String): List<String> {
        val errors = mutableListOf<String>()
        val correctWords = correctLine.split(" ")
        val rawWords = rawLine.split(" ")

        // Compare word by word and identify errors
        for (i in correctWords.indices) {
            if (i >= rawWords.size || correctWords[i] != rawWords[i]) {
                errors.add("Error at word ${i + 1}: expected '${correctWords[i]}', but found '${rawWords.getOrElse(i) { "" }}'")
            }
        }

        // Check for any extra words in the raw line
        if (rawWords.size > correctWords.size) {
            errors.add("Extra words in the raw line.")
        }

        return errors
    }




}