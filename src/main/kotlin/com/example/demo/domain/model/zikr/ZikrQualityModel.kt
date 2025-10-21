package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrQualityDto
import java.time.Instant
import java.time.Instant.now


data class ZikrQualityModel (
    val id: String = generateUUID(),

    val zikrId: String,
    val text: String,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)


fun ZikrQualityModel.toDto() = ZikrQualityDto(
    id = this.id,
    zikrId = this.zikrId,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)


