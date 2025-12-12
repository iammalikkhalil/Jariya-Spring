package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrAudioEntity
import com.example.demo.data.entity.ZikrEntity
import com.example.demo.domain.model.zikr.ZikrAudioModel
import java.util.*

fun ZikrAudioEntity.toModel(): ZikrAudioModel =
    ZikrAudioModel(
        id = id.toString(),
        zikrId = zikr.id.toString(),
        audioUrl = audioUrl,
        languageCode = languageCode,
        duration = duration,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrAudioModel.toEntity(zikrEntity: ZikrEntity): ZikrAudioEntity =
    ZikrAudioEntity(
        id = UUID.fromString(id),
        zikr = zikrEntity,
        audioUrl = audioUrl,
        languageCode = languageCode,
        duration = duration,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
