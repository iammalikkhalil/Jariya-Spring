package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrEntity
import com.example.demo.data.entity.ZikrTagEntity
import com.example.demo.data.entity.ZikrTagMapEntity
import com.example.demo.domain.model.zikr.ZikrTagMapModel
import java.util.UUID

fun ZikrTagMapEntity.toModel(): ZikrTagMapModel =
    ZikrTagMapModel(
        id = id.toString(),
        zikrId = zikr.id.toString(),
        tagId = tag.id.toString(),
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

fun ZikrTagMapModel.toEntity(
    zikrEntity: ZikrEntity,
    tagEntity: ZikrTagEntity
): ZikrTagMapEntity =
    ZikrTagMapEntity(
        id = UUID.fromString(id),
        zikr = zikrEntity,
        tag = tagEntity,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
