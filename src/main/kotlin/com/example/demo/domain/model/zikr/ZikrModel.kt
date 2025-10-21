package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrDto
import java.time.Instant
import java.time.Instant.now


data class ZikrModel(
    val id: String = generateUUID(),

    val textAr: String,
    val transliteration: String? =null,
    val titleEn: String? = null,
    val titleUr: String? = null,
    val quantityNotes: String?= null,
    val sourceNotes: String?= null,
    val isQuran: Boolean = false,
    val isHadith: Boolean = false,
    val isVerified: Boolean = false,
    val charCount: Int = 0,
    val verifiedByName: String?= null,
    val verifiedDate: Instant?= null,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)

fun ZikrModel.toDto() : ZikrDto = ZikrDto(
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



data class BulkInsertResponse(
    val message: String,
    val totalEntries: Int,
    val successfulEntries: Int,
    val failedEntries: Int,
    val successfulDetails: List<SuccessfulDetail> = emptyList()
)

data class SuccessfulDetail(
    val index: Int,
    val id: String
)
