package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrHadithDto
import java.time.Instant
import java.time.Instant.now


data class ZikrHadithModel (
    val id: String = generateUUID(),

    val zikrId: String,
    val textAr: String,
    val reference: String,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)


fun ZikrHadithModel.toDto() = ZikrHadithDto(
    id = this.id,
    zikrId = this.zikrId,
    textAr = this.textAr,
    reference = this.reference,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)