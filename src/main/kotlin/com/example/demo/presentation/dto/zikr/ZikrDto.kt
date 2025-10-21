package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrModel


data class ZikrDto(
    val id: String,
    val textAr: String,
    val titleEn: String?,
    val titleUr: String?,
    val transliteration: String? = null,
    val quantityNotes: String? = null,
    val sourceNotes: String? = null,
    val isQuran: Boolean,
    val isHadith: Boolean,
    val isVerified: Boolean,
    val charCount: Int,
    val verifiedByName: String? = null,
    val verifiedDate: Instant? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)

fun ZikrDto.toDomain() : ZikrModel = ZikrModel(
    id = this.id,
    textAr = this.textAr,
    transliteration = this.transliteration,
    quantityNotes = this.quantityNotes,
    sourceNotes = this.sourceNotes,
    isQuran = this.isQuran,
    isHadith = this.isHadith,
    isVerified = this.isVerified,
    charCount = this.charCount,
    verifiedByName = this.verifiedByName,
    verifiedDate = this.verifiedDate,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt,
    titleEn = this.titleEn,
    titleUr = this.titleUr
)


data class CsvZikrDto(
    val id: String?,
    val textAr: String,
    val titleEn: String?,
    val titleUr: String?,
    val transliteration: String?,
    val isQuran: Boolean?,
    val isHadith: Boolean?,
    val reference: String?,
    val charCount: Int?,
    val translationUrdu: String?,
    val translationEnglish: String?,
    val rewards: List<String>?,
    val qualities: List<String>?,
    val hadith: String?,
    val hadithReference: String?,
    val hadithTranslationUr: String?,
    val hadithTranslationEn: String?
)
