package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrGoalEntity
import com.example.demo.data.entity.ZikrGoalMapEntity
import com.example.demo.domain.model.zikr.ZikrGoalMapModel
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrGoalMapDto
import java.time.Instant
import java.util.UUID

// DTO -> Domain Model
fun ZikrGoalMapDto.toModel() = ZikrGoalMapModel(
    id = this.id ?: generateUUID(),

    zikrId = this.zikrId,
    goalId = this.goalId,

    countType = this.countType,
    countValue = this.countValue,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt ?: Instant.now(),
    updatedAt = this.updatedAt ?: Instant.now(),

    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

// Domain Model -> DTO
fun ZikrGoalMapModel.toDto() = ZikrGoalMapDto(
    id = this.id,

    zikrId = this.zikrId,
    goalId = this.goalId,

    countType = this.countType,
    countValue = this.countValue,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt,
    updatedAt = this.updatedAt,

    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

// Entity -> Domain Model
fun ZikrGoalMapEntity.toDomain() = ZikrGoalMapModel(
    id = this.id.toString(),

    zikrId = this.zikr.id.toString(),
    goalId = this.goal.id.toString(),

    countType = this.countType,
    countValue = this.countValue,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!,

    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

// Domain Model -> Entity
fun ZikrGoalMapModel.toEntity(
    zikr: ZikrEntity,
    goal: ZikrGoalEntity
) = ZikrGoalMapEntity(
    id = UUID.fromString(this.id),

    zikr = zikr,
    goal = goal,

    countType = this.countType,
    countValue = this.countValue,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
