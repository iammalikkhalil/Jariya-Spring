package com.example.demo.domain.model.quran

import java.time.Instant

data class QuranAyahModel(
    val globalAyahId: Long = 0,
    val surahId: Int,
    val ayahNumberInSurah: Int,
    val textArabic: String,
    val ayahKey: String,
    val juzId: Int,
    val pageNumber: Int,
    val rukuId: Int,
    val hizbQuarterId: Int,
    val manzilId: Int,
    val isSajda: Boolean = false,
    val sajdaType: String? = null,
    val textArabicUthmani: String? = null,
    val textArabicNoTashkeel: String? = null,
    val wordCount: Int,
    val charCount: Int,
//    val createdAt: Instant = Instant.now(),
//    val updatedAt: Instant = Instant.now()
)
