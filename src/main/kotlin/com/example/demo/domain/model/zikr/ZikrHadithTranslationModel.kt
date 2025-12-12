package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrHadithTranslationModel(

    val id: String,

    val hadithId: String,
    val translation: String,
    val languageCode: String,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
