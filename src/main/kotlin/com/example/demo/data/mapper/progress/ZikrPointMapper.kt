package com.example.demo.data.mapper.progress

import com.example.demo.data.entity.*
import com.example.demo.domain.enums.SourceType
import com.example.demo.domain.model.progress.ZikrPointModel
import java.util.*

fun ZikrPointEntity.toModel(): ZikrPointModel =
    ZikrPointModel(
        id = id.toString(),
        userId = user.id.toString(),
        zikrId = zikr?.id?.toString(),
        progressId = progress?.id?.toString(),
        level = level,
        points = points,
        sourceType = SourceType.valueOf(sourceType),
        sourceUser = sourceUser.id.toString(),
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )

fun ZikrPointModel.toEntity(
    userEntity: UserEntity,
    sourceUserEntity: UserEntity,
    zikrEntity: ZikrEntity? = null,
    progressEntity: ZikrProgressEntity? = null
): ZikrPointEntity =
    ZikrPointEntity(
        id = UUID.fromString(id),
        user = userEntity,
        zikr = zikrEntity,
        progress = progressEntity,
        level = level,
        points = points,
        sourceType = sourceType.name,
        sourceUser = sourceUserEntity,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
