package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrCollectionEntity
import com.example.demo.domain.model.zikr.ZikrCollectionModel
import java.util.UUID

fun ZikrCollectionEntity.toModel(): ZikrCollectionModel =
    ZikrCollectionModel(
        id = id.toString(),
        text = text,
        isFeatured = isFeatured,
        description = description,
        orderIndex = orderIndex,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrCollectionModel.toEntity(): ZikrCollectionEntity =
    ZikrCollectionEntity(
        id = UUID.fromString(id),
        text = text,
        isFeatured = isFeatured,
        description = description,
        orderIndex = orderIndex,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
