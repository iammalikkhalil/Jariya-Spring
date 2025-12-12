package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrRewardEntity
import com.example.demo.domain.model.zikr.ZikrRewardModel
import java.util.UUID

fun ZikrRewardEntity.toModel(): ZikrRewardModel =
    ZikrRewardModel(
        id = id.toString(),
        zikrId = zikr.id.toString(),
        text = text,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrRewardModel.toEntity(
    zikrEntity: ZikrEntity
): ZikrRewardEntity =
    ZikrRewardEntity(
        id = UUID.fromString(id),
        zikr = zikrEntity,
        text = text,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
