package com.example.demo.domain.model.quran

import java.time.Instant

data class QuranRukuModel(
    val rukuId: Long = 0,
    val surahId: Int,
    val rukuNumberInSurah: Int,
    val startGlobalAyahId: Int,
    val endGlobalAyahId: Int,
//    val createdAt: Instant = Instant.now(),
//    val updatedAt: Instant = Instant.now()
)
