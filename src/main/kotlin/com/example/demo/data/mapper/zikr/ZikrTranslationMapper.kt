package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrTranslationEntity
import com.example.demo.domain.model.zikr.ZikrTranslationModel
import java.util.*

fun ZikrTranslationEntity.toModel(): ZikrTranslationModel =
    ZikrTranslationModel(
        id = id.toString(),
        zikrId = zikr.id.toString(),
        translation = translation,
        languageCode = languageCode,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrTranslationModel.toEntity(
    zikrEntity: ZikrEntity
): ZikrTranslationEntity =
    ZikrTranslationEntity(
        id = UUID.fromString(id),
        zikr = zikrEntity,
        translation = translation,
        languageCode = languageCode,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
