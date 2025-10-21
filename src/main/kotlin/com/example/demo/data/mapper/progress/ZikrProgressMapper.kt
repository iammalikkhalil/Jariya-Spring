package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.UserEntity
import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrProgressEntity
import com.example.demo.domain.model.progress.ZikrProgressModel
import java.util.*

fun ZikrProgressEntity.toModel(): ZikrProgressModel =
    ZikrProgressModel(
        id = id.toString(),
        userId = user.id.toString(),
        zikrId = zikr?.id?.toString() ?: "",
        deviceId = deviceId?.toString(),
        sessionId = sessionId?.toString(),
        source = source,
        count = count,
        processedLevels = processedLevels,
        isStarted = isStarted,
        isCompleted = isCompleted,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncedAt = syncedAt
    )

fun ZikrProgressModel.toEntity(
    userEntity: UserEntity,
    zikrEntity: ZikrEntity?
): ZikrProgressEntity =
    ZikrProgressEntity(
        id = UUID.fromString(id),
        user = userEntity,
        zikr = zikrEntity,
        deviceId = deviceId?.let { UUID.fromString(it) },
        sessionId = sessionId?.let { UUID.fromString(it) },
        source = source,
        count = count,
        processedLevels = processedLevels,
        isStarted = isStarted,
        isCompleted = isCompleted,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncedAt = syncedAt
    )
