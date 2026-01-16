package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.GoalProgressEntity
import com.example.demo.data.entity.UserEntity
import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrGoalEntity
import com.example.demo.data.entity.ZikrProgressEntity
import com.example.demo.domain.model.progress.GoalProgressModel
import com.example.demo.domain.model.progress.ZikrProgressModel
import com.example.demo.infrastructure.utils.toUUID
import com.example.demo.presentation.dto.sync.GoalProgressSyncDto
import java.time.Instant
import java.util.*

fun GoalProgressEntity.toModel(): GoalProgressModel = GoalProgressModel(
    id = id.toString(),
    userId = user,
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
    zikrEntity: ZikrEntity?,
    goalEntity: ZikrGoalEntity?,
): GoalProgressEntity = GoalProgressEntity(
    id = UUID.fromString(id),
    user = userId,
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


fun GoalProgressSyncDto.toEntity(
    existing: GoalProgressEntity?
): GoalProgressEntity {

    val entity = existing ?: GoalProgressEntity(
        id = id.toUUID(),
        count = 0,
        charCount = 0,
        createdAt = Instant.now(),
        updatedAt = Instant.now(),
        user = null,
    )

    entity.user = existing?.user

    // ZIKR
    entity.zikr = existing?.zikr ?: zikrId?.let { ZikrEntity(it.toUUID()) }

    // GOAL
    entity.goal = existing?.goal ?: goalId?.let { ZikrGoalEntity(it.toUUID()) }

    // Other fields
    entity.deviceId = deviceId?.toUUID()
    entity.sessionId = sessionId?.toUUID()
    entity.type = type
    entity.count = count?.toIntOrNull() ?: entity.count
    entity.charCount = charCount?.toIntOrNull() ?: entity.charCount
    entity.processedLevels = levels?.toIntOrNull()
    entity.isStarted = isStarted?.toBoolean() ?: false
    entity.isCompleted = isCompleted?.toBoolean() ?: false
    entity.updatedAt = updatedAt?.let { Instant.ofEpochMilli(it) } ?: Instant.now()
    entity.syncedAt = syncedAt?.let { Instant.parse(it) }
    entity.isDeleted = isDeleted ?: false
    entity.deletedAt = if (isDeleted == true) Instant.now() else null

    return entity
}


