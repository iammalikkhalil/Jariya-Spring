package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrCollectionMapModel
import kotlin.String


data class ZikrCollectionMapDto (
    val id: String,

    val zikrId: String,
    val collectionId: String,

    val countType: String,
    val countValue: Int,
    val orderIndex: Int,


    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)


fun ZikrCollectionMapDto.toDomain() = ZikrCollectionMapModel(
    id = this.id,
    zikrId = this.zikrId,
    collectionId = this.collectionId,
    countType = this.countType,
    countValue = this.countValue,
    orderIndex = this.orderIndex,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt,
)