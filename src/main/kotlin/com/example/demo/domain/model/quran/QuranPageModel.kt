package com.example.demo.domain.model.quran

import java.time.Instant

data class QuranPageModel(
    val pageNumber: Long = 0,
    val startGlobalAyahId: Int,
    val endGlobalAyahId: Int,
//    val createdAt: Instant = Instant.now(),
//    val updatedAt: Instant = Instant.now()
)
