package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrTagMapDto
import java.time.Instant
import java.time.Instant.now


data class ZikrTagMapModel (
    val id: String = generateUUID(),

    val zikrId: String,
    val tagId: String,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)


fun ZikrTagMapModel.toDto() = ZikrTagMapDto(
    id = this.id,
    zikrId = this.zikrId,
    tagId = this.tagId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

