package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrAudioModel(

    val id: String,

    val zikrId: String,
    val audioUrl: String,
    val languageCode: String,
    val duration: Int,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
