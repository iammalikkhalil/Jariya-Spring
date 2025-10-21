package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.UserDailySummaryEntity
import com.example.demo.data.entity.UserEntity
import com.example.demo.domain.model.progress.UserDailySummaryModel
import java.util.*

fun UserDailySummaryEntity.toModel(): UserDailySummaryModel =
    UserDailySummaryModel(
        id = id.toString(),
        userId = user.id.toString(),
        zikrCount = zikrCount,
        points = points,
        date = date,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )

fun UserDailySummaryModel.toEntity(
    userEntity: UserEntity
): UserDailySummaryEntity =
    UserDailySummaryEntity(
        id = UUID.fromString(id),
        user = userEntity,
        zikrCount = zikrCount,
        points = points,
        date = date,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
