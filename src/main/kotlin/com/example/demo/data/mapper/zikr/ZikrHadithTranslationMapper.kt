package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrHadithEntity
import com.example.demo.data.entity.ZikrHadithTranslationEntity
import com.example.demo.domain.model.zikr.ZikrHadithTranslationModel
import java.util.UUID

fun ZikrHadithTranslationEntity.toModel(): ZikrHadithTranslationModel =
    ZikrHadithTranslationModel(
        id = id.toString(),
        hadithId = hadith.id.toString(),
        translation = translation,
        languageCode = languageCode,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrHadithTranslationModel.toEntity(
    hadithEntity: ZikrHadithEntity
): ZikrHadithTranslationEntity =
    ZikrHadithTranslationEntity(
        id = UUID.fromString(id),
        hadith = hadithEntity,
        translation = translation,
        languageCode = languageCode,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
