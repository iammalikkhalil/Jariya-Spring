package com.example.demo.presentation.dto.zikr

import java.time.Instant.now
import java.time.Instant

import com.example.demo.infrastructure.utils.generateUUID


data class GetZikrByIdDto(
    val id: String
)



data class TimeDto(
    val updatedAt: String
)



data class CreateZikrDto(
    val id: String? = null,
    val textAr: String = "",
    val titleEn: String? = "",
    val titleUr: String? = "",
    val transliteration: String? = null,
    val quantityNotes: String? = null,
    val sourceNotes: String? = null,
    val isQuran: Boolean? = null,
    val isHadith: Boolean? = null,
    val isVerified: Boolean? = null,
    val charCount: Int = 0,
    val verifiedByName: String? = null,
    val verifiedDate: Instant? = null,
    val createdAt: Instant?= null,
    val updatedAt: Instant?= null,
    val isDeleted: Boolean? = null,
    val deletedAt: Instant?= null
)



data class ZikrAudioDtoRequest(
    val id: String = generateUUID(),

    val zikrId: String = generateUUID(),
    val audioUrl: String = generateUUID(),
    val languageCode: String = "en",
    val duration: Int = 1234,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrCollectionDtoRequest(
    val id: String = generateUUID(),

    val text: String = "hello",
    val isFeatured: Boolean = true,
    val description: String? = null,
    val orderIndex: Int = 2,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrCollectionMapDtoRequest(
    val id: String = generateUUID(),

    val zikrId: String = generateUUID(),
    val collectionId: String = generateUUID(),

    val countType: String = "down",
    val countValue: Int = 33,
    val orderIndex: Int = 1,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrHadithDtoRequest(
    val id: String = generateUUID(),

    val zikrId: String = generateUUID(),
    val textAr: String = generateUUID(),
    val reference: String = generateUUID(),

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrHadithTranslationDtoRequest(
    val id: String = generateUUID(),

    val hadithId: String = generateUUID(),
    val translation: String = generateUUID(),
    val languageCode: String = generateUUID(),

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrQualityDtoRequest(
    val id: String = generateUUID(),

    val zikrId: String = generateUUID(),
    val text: String = generateUUID(),

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrRewardDtoRequest(
    val id: String = generateUUID(),

    val zikrId: String = generateUUID(),
    val text: String = generateUUID(),

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrTagDtoRequest(
    val id: String = generateUUID(),

    val text: String = generateUUID(),

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrTagMapDtoRequest(
    val id: String = generateUUID(),

    val zikrId: String = generateUUID(),
    val tagId: String = generateUUID(),

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



data class ZikrTranslationDtoRequest(
    val id: String = generateUUID(),

    val zikrId: String = generateUUID(),
    val translation: String = generateUUID(),
    val languageCode: String = generateUUID(),

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)