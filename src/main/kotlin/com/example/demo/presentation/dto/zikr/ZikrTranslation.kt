package com.example.demo.presentation.dto.zikr

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant
import com.example.demo.domain.model.zikr.ZikrTranslationModel


data class ZikrTranslationDto(

    @field:NotBlank(message = "id is required")
    val id: String,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "translation text is required")
    @field:Size(max = 5000, message = "translation must not exceed 5000 characters")
    val translation: String,

    @field:NotBlank(message = "languageCode is required")
    @field:Size(max = 10, message = "languageCode must not exceed 10 characters")
    val languageCode: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant? = null
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