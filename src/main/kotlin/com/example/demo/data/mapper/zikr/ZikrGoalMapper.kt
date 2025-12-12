package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrGoalEntity
import com.example.demo.domain.enums.zikr.GoalSourceType
import com.example.demo.domain.enums.zikr.GoalType
import com.example.demo.domain.model.zikr.ZikrGoalModel
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrGoalDto
import java.time.Instant
import java.util.UUID


fun ZikrGoalDto.toModel() = ZikrGoalModel(
    id = this.id ?: generateUUID(),

    title = this.title,
    description = this.description,
    arabicText = this.arabicText,

    type = this.type?.let { GoalType.fromStringOrThrow(it) },
    targetValue = this.targetValue,
    unit = this.unit,

    sourceType = this.sourceType?.let { GoalSourceType.fromStringOrThrow(it) },
    sourceRef = this.sourceRef,
    verifiedBy = this.verifiedBy,

    showFrom = this.showFrom,
    showUntil = this.showUntil,

    isRecurring = this.isRecurring,
    recurrence = this.recurrence,

    isFeatured = this.isFeatured,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt ?: Instant.now(),
    updatedAt = this.updatedAt ?: Instant.now(),

    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)



fun ZikrGoalModel.toDto() = ZikrGoalDto(
    id = this.id,

    title = this.title,
    description = this.description,
    arabicText = this.arabicText,

    type = this.type.toString(),
    targetValue = this.targetValue,
    unit = this.unit,

    sourceType = this.sourceType.toString(),
    sourceRef = this.sourceRef,
    verifiedBy = this.verifiedBy,

    showFrom = this.showFrom,
    showUntil = this.showUntil,

    isRecurring = this.isRecurring,
    recurrence = this.recurrence,

    isFeatured = this.isFeatured,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt,
    updatedAt = this.updatedAt,

    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

fun ZikrGoalEntity.toModel() = ZikrGoalModel(
    id = this.id.toString(),

    title = this.title,
    description = this.description,
    arabicText = this.arabicText,

    type = this.type,
    targetValue = this.targetValue,
    unit = this.unit,

    sourceType = this.sourceType,
    sourceRef = this.sourceRef,
    verifiedBy = this.verifiedBy,

    showFrom = this.showFrom,
    showUntil = this.showUntil,

    isRecurring = this.isRecurring,
    recurrence = this.recurrence,

    isFeatured = this.isFeatured,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!,

    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

fun ZikrGoalModel.toEntity() = ZikrGoalEntity(
    id = UUID.fromString(this.id),

    title = this.title,
    description = this.description,
    arabicText = this.arabicText,

    type = this.type,
    targetValue = this.targetValue,
    unit = this.unit,

    sourceType = this.sourceType,
    sourceRef = this.sourceRef,
    verifiedBy = this.verifiedBy,

    showFrom = this.showFrom,
    showUntil = this.showUntil,

    isRecurring = this.isRecurring,
    recurrence = this.recurrence,

    isFeatured = this.isFeatured,
    orderIndex = this.orderIndex,

    createdAt = this.createdAt,
    updatedAt = this.updatedAt,

    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
