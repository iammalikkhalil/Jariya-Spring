package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrQualityEntity
import com.example.demo.domain.model.zikr.ZikrQualityModel
import java.util.UUID

fun ZikrQualityEntity.toModel(): ZikrQualityModel =
    ZikrQualityModel(
        id = id.toString(),
        zikrId = zikr.id.toString(),
        text = text,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrQualityModel.toEntity(
    zikrEntity: ZikrEntity
): ZikrQualityEntity =
    ZikrQualityEntity(
        id = UUID.fromString(id),
        zikr = zikrEntity,
        text = text,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
