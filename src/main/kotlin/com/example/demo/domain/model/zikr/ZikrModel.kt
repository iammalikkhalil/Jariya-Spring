package com.example.demo.domain.model.zikr

import java.time.Instant

data class ZikrModel(

    val id: String,

    val textAr: String,
    val transliteration: String?,
    val titleEn: String?,
    val titleUr: String?,
    val quantityNotes: String?,
    val sourceNotes: String?,
    val isQuran: Boolean,
    val isHadith: Boolean,
    val isVerified: Boolean,
    val charCount: Int,
    val verifiedByName: String?,
    val verifiedDate: Instant?,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?
)
