package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.GoalProgressEntity
import com.example.demo.data.entity.UserEntity
import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrGoalEntity
import com.example.demo.data.entity.ZikrProgressEntity
import com.example.demo.domain.model.progress.GoalProgressModel
import com.example.demo.domain.model.progress.ZikrProgressModel
import java.util.*

fun GoalProgressEntity.toModel(): GoalProgressModel =
    GoalProgressModel(
        id = id.toString(),
        userId = user.id.toString(),
        zikrId = zikr?.id?.toString() ?: "",
        goalId = goal?.id?.toString() ?: "",
        deviceId = deviceId?.toString(),
        sessionId = sessionId?.toString(),
        type = type,
        count = count,
        charCount = charCount,
        processedLevels = processedLevels,
        isStarted = isStarted,
        isCompleted = isCompleted,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncedAt = syncedAt
    )

fun GoalProgressModel.toEntity(
    userEntity: UserEntity,
    zikrEntity: ZikrEntity?,
    goalEntity: ZikrGoalEntity?,
): GoalProgressEntity =
    GoalProgressEntity(
        id = UUID.fromString(id),
        user = userEntity,
        zikr = zikrEntity,
        goal = goalEntity,
        deviceId = deviceId?.let { UUID.fromString(it) },
        sessionId = sessionId?.let { UUID.fromString(it) },
        type = type,
        count = count,
        charCount = charCount,
        processedLevels = processedLevels,
        isStarted = isStarted,
        isCompleted = isCompleted,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncedAt = syncedAt
    )
