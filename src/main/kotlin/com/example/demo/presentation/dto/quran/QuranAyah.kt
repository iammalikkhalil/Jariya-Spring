package com.example.demo.presentation.dto.quran


data class QuranAyahRequestDTO(
    val surahNumber: Int,
    val ayahStartNumber: Int,
    val ayahEndNumber: Int,
    val rawText: String = ""
)


data class ComparisonResult(
    val correctText: String,
    val rawText: String,
    val errors: List<String>
)



data class LineComparisonResult(
    val correctLine: String,
    val rawText: String,
    val errors: List<String>
)



