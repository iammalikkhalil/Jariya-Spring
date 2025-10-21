package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrCollectionDto
import java.time.Instant
import java.time.Instant.now


data class ZikrCollectionModel (
    val id: String = generateUUID(),

    val text: String,
    val isFeatured: Boolean = false,
    val description: String?,
    val orderIndex: Int = 1,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)


fun ZikrCollectionModel.toDto() = ZikrCollectionDto(
    id = this.id,
    text = this.text,
    isFeatured = this.isFeatured,
    description = this.description,
    orderIndex = this.orderIndex,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)