package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrTranslationDto
import java.time.Instant
import java.time.Instant.now


data class ZikrTranslationModel (
    val id: String = generateUUID(),

    val zikrId: String,
    val translation: String,
    val languageCode: String,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)

fun ZikrTranslationModel.toDto() = ZikrTranslationDto(
    id = this.id,
    zikrId = this.zikrId,
    translation = this.translation,
    languageCode = this.languageCode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)