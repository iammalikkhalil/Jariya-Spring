package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrCollectionMapRepository
import com.example.demo.presentation.dto.zikr.ZikrCollectionMapDto
import com.example.demo.presentation.dto.zikr.ZikrCollectionMapDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrCollectionMapService(
    private val zikrCollectionMapRepository: ZikrCollectionMapRepository
) {

    fun getAllZikrCollectionMaps() = zikrCollectionMapRepository.getAllZikrCollectionMaps()

    fun getZikrCollectionMapById(id: String) = zikrCollectionMapRepository.getZikrCollectionMapById(id)

    fun createZikrCollectionMap(body: ZikrCollectionMapDtoRequest): Boolean {
        val dto = ZikrCollectionMapDto(
            id = body.id,
            zikrId = body.zikrId,
            collectionId = body.collectionId,
            countType = body.countType,
            countValue = body.countValue,
            orderIndex = body.orderIndex,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrCollectionMapRepository.createZikrCollectionMap(dto.toDomain())
    }

    fun updateZikrCollectionMap(body: ZikrCollectionMapDtoRequest): Boolean {
        val dto = ZikrCollectionMapDto(
            id = body.id,
            zikrId = body.zikrId,
            collectionId = body.collectionId,
            countType = body.countType,
            countValue = body.countValue,
            orderIndex = body.orderIndex,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrCollectionMapRepository.updateZikrCollectionMap(dto.toDomain())
    }

    fun deleteZikrCollectionMap(id: String) = zikrCollectionMapRepository.deleteZikrCollectionMap(id)

    fun getUpdatedZikrCollectionMaps(updatedAt: Instant) =
        zikrCollectionMapRepository.getUpdatedZikrCollectionMaps(updatedAt)
}
