package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.UserTotalPointEntity
import com.example.demo.domain.model.progress.UserTotalPointsModel
import java.util.*

fun UserTotalPointEntity.toModel(): UserTotalPointsModel =
    UserTotalPointsModel(
        id = id.toString(),
        total = total,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )

fun UserTotalPointsModel.toEntity(): UserTotalPointEntity =
    UserTotalPointEntity(
        id = UUID.fromString(id),
        total = total,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
