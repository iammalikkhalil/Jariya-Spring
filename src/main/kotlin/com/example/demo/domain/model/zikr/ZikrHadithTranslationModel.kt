package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrHadithTranslationDto
import java.time.Instant
import java.time.Instant.now


data class ZikrHadithTranslationModel (
    val id: String = generateUUID(),

    val hadithId: String,
    val translation: String,
    val languageCode: String,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)


fun ZikrHadithTranslationModel.toDto() = ZikrHadithTranslationDto(
    id = this.id,
    hadithId = this.hadithId,
    translation = this.translation,
    languageCode = this.languageCode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

