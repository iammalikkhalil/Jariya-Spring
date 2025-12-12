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

    fun getAllZikrCollectionMaps() =
        zikrCollectionMapRepository.getAllZikrCollectionMaps()

    fun getZikrCollectionMapById(id: String) =
        zikrCollectionMapRepository.getZikrCollectionMapById(id)

    fun createZikrCollectionMap(body: ZikrCollectionMapDtoRequest): Boolean =
        zikrCollectionMapRepository.createZikrCollectionMap(body.toDto().toDomain())

    fun updateZikrCollectionMap(body: ZikrCollectionMapDtoRequest): Boolean =
        zikrCollectionMapRepository.updateZikrCollectionMap(body.toDto().toDomain())

    fun deleteZikrCollectionMap(id: String) =
        zikrCollectionMapRepository.deleteZikrCollectionMap(id)

    fun getUpdatedZikrCollectionMaps(updatedAt: Instant) =
        zikrCollectionMapRepository.getUpdatedZikrCollectionMaps(updatedAt)


    // ------------------------------------------------------------
    // 🔹 Private mapper: Request → DTO
    // ------------------------------------------------------------
    private fun ZikrCollectionMapDtoRequest.toDto() = ZikrCollectionMapDto(
        id = this.id,
        zikrId = this.zikrId,
        collectionId = this.collectionId,
        countType = this.countType,
        countValue = this.countValue,
        orderIndex = this.orderIndex,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
