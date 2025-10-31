package com.example.demo.data.mapper.zikr

import com.example.demo.data.entity.ZikrCollectionEntity
import com.example.demo.data.entity.ZikrCollectionMapEntity
import com.example.demo.data.entity.ZikrEntity
import com.example.demo.domain.model.zikr.ZikrCollectionMapModel
import com.example.demo.infrastructure.utils.Log
import java.util.*

fun ZikrCollectionMapEntity.toModel(): ZikrCollectionMapModel{
    Log.info("🧩 Mapping: ${this.id} (zikr=${this.zikr.id}, collection=${this.collection.id})")
   return ZikrCollectionMapModel(
        id = id.toString(),
        zikrId = zikr.id.toString(),
        collectionId = collection.id.toString(),
        countType = countType,
        countValue = countValue,
        orderIndex = orderIndex,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )
}

fun ZikrCollectionMapModel.toEntity(
    zikrEntity: ZikrEntity,
    collectionEntity: ZikrCollectionEntity
): ZikrCollectionMapEntity {
    Log.info("🧩 Mapping: ${this.id} (zikr=${zikrEntity.id}, collection=${collectionEntity.id})")

    return   ZikrCollectionMapEntity(
        id = UUID.fromString(id),
        zikr = zikrEntity,
        collection = collectionEntity,
        countType = countType,
        countValue = countValue,
        orderIndex = orderIndex,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

}




