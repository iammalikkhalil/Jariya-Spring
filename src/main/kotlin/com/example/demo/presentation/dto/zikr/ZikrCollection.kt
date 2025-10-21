package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrCollectionModel


data class ZikrCollectionDto (
    val id: String,

    val text: String,
    val isFeatured: Boolean,
    val description: String?,
    val orderIndex: Int,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)

fun ZikrCollectionDto.toDomain() = ZikrCollectionModel(
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
