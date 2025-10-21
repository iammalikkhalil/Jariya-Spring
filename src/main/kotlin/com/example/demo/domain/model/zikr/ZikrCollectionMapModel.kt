package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrCollectionMapDto
import java.time.Instant
import java.time.Instant.now


data class ZikrCollectionMapModel (
    val id: String = generateUUID(),

    val zikrId: String,
    val collectionId: String,

    val countType: String = "down",
    val countValue: Int = 33,
    val orderIndex: Int = 1,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)





fun ZikrCollectionMapModel.toDto() = ZikrCollectionMapDto(
    id = this.id,
    zikrId = this.zikrId,
    collectionId = this.collectionId,
    countType = this.countType,
    countValue = this.countValue,
    orderIndex = this.orderIndex,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

