package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrQualityModel


data class ZikrQualityDto (
    val id: String,

    val zikrId: String,
    val text: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)


fun ZikrQualityDto.toDomain() = ZikrQualityModel(
    id = this.id,
    zikrId = this.zikrId,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)