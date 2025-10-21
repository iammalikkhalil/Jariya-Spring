package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrTagMapModel


data class ZikrTagMapDto (
    val id: String,

    val zikrId: String,
    val tagId: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)


fun ZikrTagMapDto.toDomain() = ZikrTagMapModel(
    id = this.id,
    zikrId = this.zikrId,
    tagId = this.tagId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)