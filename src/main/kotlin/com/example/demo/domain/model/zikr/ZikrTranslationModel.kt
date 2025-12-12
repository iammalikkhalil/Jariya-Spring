package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrTranslationModel(

    val id: String,

    val zikrId: String,
    val translation: String,
    val languageCode: String,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
