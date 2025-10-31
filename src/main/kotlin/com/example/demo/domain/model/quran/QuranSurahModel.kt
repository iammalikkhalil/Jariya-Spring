package com.example.demo.domain.model.quran

import java.time.Instant

data class QuranSurahModel(
    val surahId: Long = 0,
    val nameArabic: String,
    val nameTransliteration: String,
    val nameEnglish: String,
    val ayahCount: Int,
    val revelationPlace: String,
    val chronologicalOrder: Int,
    val rukuCount: Int,
    val hasBismillah: Boolean = true,
    val mukattaatLetters: String? = null,
    val startGlobalAyahId: Int,
    val endGlobalAyahId: Int,
//    val createdAt: Instant = Instant.now(),
//    val updatedAt: Instant = Instant.now()
)
