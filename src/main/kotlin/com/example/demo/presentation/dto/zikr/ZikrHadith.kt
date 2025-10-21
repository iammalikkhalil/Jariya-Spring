package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrHadithModel


data class ZikrHadithDto (
    val id: String,

    val zikrId: String,
    val textAr: String,
    val reference: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)

fun ZikrHadithDto.toDomain() = ZikrHadithModel(
    id = this.id,
    zikrId = this.zikrId,
    textAr = this.textAr,
    reference = this.reference,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)