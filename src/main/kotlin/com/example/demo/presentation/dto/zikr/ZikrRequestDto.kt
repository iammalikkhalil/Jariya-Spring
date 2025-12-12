package com.example.demo.presentation.dto.zikr

import jakarta.validation.constraints.*
import java.time.Instant

// ---------------------------
// BASIC DTOs
// ---------------------------

data class IdRequestDto(
    @field:NotBlank(message = "ID is required")
    val id: String
)

data class GetZikrByIdDto(
    @field:NotBlank(message = "Zikr ID is required")
    val id: String
)

data class TimeDto(
    @field:NotBlank(message = "updatedAt timestamp is required (ISO-8601 format)")
    val updatedAt: String
)


// ---------------------------
// CREATE ZIKR DTO
// ---------------------------

data class CreateZikrDto(

    val id: String? = null,

    @field:NotBlank(message = "Arabic text (textAr) is required")
    val textAr: String,

    @field:Size(max = 500, message = "titleEn cannot exceed 500 characters")
    val titleEn: String? = null,

    @field:Size(max = 500, message = "titleUr cannot exceed 500 characters")
    val titleUr: String? = null,

    val transliteration: String? = null,
    val quantityNotes: String? = null,
    val sourceNotes: String? = null,

    val isQuran: Boolean? = null,
    val isHadith: Boolean? = null,
    val isVerified: Boolean? = null,

    val charCount: Int = 0,

    @field:Size(max = 2000, message = "verifiedByName cannot exceed 2000 characters")
    val verifiedByName: String? = null,

    val verifiedDate: Instant? = null,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,

    val isDeleted: Boolean? = null,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR AUDIO DTO REQUEST
// ---------------------------

data class ZikrAudioDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "Audio URL is required")
    val audioUrl: String,

    @field:NotBlank(message = "Language code is required")
    val languageCode: String,

    @field:NotNull(message = "Duration is required")
    val duration: Int,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR COLLECTION DTO
// ---------------------------

data class ZikrCollectionDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "Collection text is required")
    val text: String,

    val isFeatured: Boolean = false,

    @field:Size(max = 2000, message = "Description cannot exceed 2000 characters")
    val description: String? = null,

    @field:Min(value = 0, message = "orderIndex cannot be negative")
    val orderIndex: Int = 1,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR COLLECTION MAP DTO
// ---------------------------

data class ZikrCollectionMapDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "collectionId is required")
    val collectionId: String,

    @field:NotBlank(message = "countType is required")
    val countType: String,

    @field:Min(value = 1, message = "countValue must be at least 1")
    val countValue: Int,

    @field:Min(value = 0, message = "orderIndex cannot be negative")
    val orderIndex: Int = 1,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR HADITH DTO
// ---------------------------

data class ZikrHadithDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "Arabic Hadith text (textAr) is required")
    val textAr: String,

    @field:NotBlank(message = "Reference is required")
    val reference: String,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR HADITH TRANSLATION DTO
// ---------------------------

data class ZikrHadithTranslationDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "Hadith ID is required")
    val hadithId: String,

    @field:NotBlank(message = "Translation is required")
    val translation: String,

    @field:NotBlank(message = "Language code is required")
    val languageCode: String,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR QUALITY DTO
// ---------------------------

data class ZikrQualityDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "Quality text is required")
    val text: String,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR REWARD DTO
// ---------------------------

data class ZikrRewardDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "Reward text is required")
    val text: String,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR TAG DTO
// ---------------------------

data class ZikrTagDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "Tag text is required")
    val text: String,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR TAG MAP DTO
// ---------------------------

data class ZikrTagMapDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "tagId is required")
    val tagId: String,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


// ---------------------------
// ZIKR TRANSLATION DTO
// ---------------------------

data class ZikrTranslationDtoRequest(

    val id: String? = null,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "Translation is required")
    val translation: String,

    @field:NotBlank(message = "Language code is required")
    val languageCode: String,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)
