package com.example.demo.presentation.dto.zikr

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant
import com.example.demo.domain.model.zikr.ZikrHadithTranslationModel


data class ZikrHadithTranslationDto(

    @field:NotBlank(message = "id is required")
    val id: String,

    @field:NotBlank(message = "hadithId is required")
    val hadithId: String,

    @field:NotBlank(message = "translation is required")
    @field:Size(max = 5000, message = "translation is too long")
    val translation: String,

    @field:NotBlank(message = "languageCode is required")
    @field:Size(max = 10, message = "languageCode is invalid")
    val languageCode: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant? = null
)


fun ZikrHadithTranslationDto.toDomain() = ZikrHadithTranslationModel(
    id = this.id,
    hadithId = this.hadithId,
    translation = this.translation.trim(),
    languageCode = this.languageCode.lowercase().trim(),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
