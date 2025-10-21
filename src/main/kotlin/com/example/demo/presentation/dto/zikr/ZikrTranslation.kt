package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrTranslationModel


data class ZikrTranslationDto (
    val id: String,

    val zikrId: String,
    val translation: String,
    val languageCode: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)

fun ZikrTranslationDto.toDomain() = ZikrTranslationModel(
    id = this.id,
    zikrId = this.zikrId,
    translation = this.translation,
    languageCode = this.languageCode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)