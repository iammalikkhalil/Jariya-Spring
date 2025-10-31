package com.example.demo.domain.model.quran

import java.time.Instant

data class QuranHizbQuarterModel(
    val hizbQuarterId: Long = 0,
    val hizbNumber: Int,
    val quarterNumberInHizb: Int,
    val startGlobalAyahId: Int,
    val endGlobalAyahId: Int,
//    val createdAt: Instant = Instant.now(),
//    val updatedAt: Instant = Instant.now()
)
