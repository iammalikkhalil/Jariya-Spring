package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrHadithEntity
import com.example.demo.domain.model.zikr.ZikrHadithModel
import java.util.UUID

fun ZikrHadithEntity.toModel(): ZikrHadithModel =
    ZikrHadithModel(
        id = id.toString(),
        zikrId = zikr.id.toString(),
        textAr = textAr,
        reference = reference,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrHadithModel.toEntity(
    zikrEntity: ZikrEntity
): ZikrHadithEntity =
    ZikrHadithEntity(
        id = UUID.fromString(id),
        zikr = zikrEntity,
        textAr = textAr,
        reference = reference,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
