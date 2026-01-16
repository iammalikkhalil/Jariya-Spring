package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.*
import com.example.demo.domain.enums.PointsSourceType
import com.example.demo.domain.model.progress.ZikrPointModel
import java.util.*

fun ZikrPointEntity.toModel(): ZikrPointModel =
    ZikrPointModel(
        id = id.toString(),
        userId = user,
        zikrId = zikr?.id?.toString(),
        progressId = progressId,
        progressType = progressType,
        level = level,
        points = points,
        pointsSourceType = PointsSourceType.valueOf(sourceType),
        sourceUser = sourceUser,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )

fun ZikrPointModel.toEntity(
    zikrEntity: ZikrEntity? = null,
): ZikrPointEntity =
    ZikrPointEntity(
        id = UUID.fromString(id),
        user = userId,
        zikr = zikrEntity,
        progressId = progressId,
        progressType = progressType,
        level = level,
        points = points,
        sourceType = pointsSourceType.name,
        sourceUser = sourceUser,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
