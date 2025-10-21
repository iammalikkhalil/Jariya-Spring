package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrHadithTranslationModel


data class ZikrHadithTranslationDto (
    val id: String,

    val hadithId: String,
    val translation: String,
    val languageCode: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)



fun ZikrHadithTranslationDto.toDomain() = ZikrHadithTranslationModel(
    id = this.id,
    hadithId = this.hadithId,
    translation = this.translation,
    languageCode = this.languageCode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

