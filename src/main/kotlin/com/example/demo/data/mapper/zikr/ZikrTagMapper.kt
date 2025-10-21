package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrTagEntity
import com.example.demo.domain.model.zikr.ZikrTagModel
import java.util.*

fun ZikrTagEntity.toModel(): ZikrTagModel =
    ZikrTagModel(
        id = id.toString(),
        text = text,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrTagModel.toEntity(): ZikrTagEntity =
    ZikrTagEntity(
        id = UUID.fromString(id),
        text = text,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
