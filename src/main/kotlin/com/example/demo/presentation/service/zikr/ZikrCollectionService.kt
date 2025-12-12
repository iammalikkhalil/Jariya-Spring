package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrCollectionRepository
import com.example.demo.presentation.dto.zikr.ZikrCollectionDto
import com.example.demo.presentation.dto.zikr.ZikrCollectionDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrCollectionService(
    private val zikrCollectionRepository: ZikrCollectionRepository
) {

    fun getAllZikrCollections() =
        zikrCollectionRepository.getAllZikrCollections()

    fun getZikrCollectionById(id: String) =
        zikrCollectionRepository.getZikrCollectionById(id)

    fun createZikrCollection(body: ZikrCollectionDtoRequest): Boolean =
        zikrCollectionRepository.createZikrCollection(body.toDto().toDomain())

    fun updateZikrCollection(body: ZikrCollectionDtoRequest): Boolean =
        zikrCollectionRepository.updateZikrCollection(body.toDto().toDomain())

    fun deleteZikrCollection(id: String) =
        zikrCollectionRepository.deleteZikrCollection(id)

    fun getUpdatedZikrCollections(updatedAt: Instant) =
        zikrCollectionRepository.getUpdatedZikrCollections(updatedAt)


    // ------------------------------------------------------------
    // 🔹 Private mapper: Request → DTO (clean & centralised)
    // ------------------------------------------------------------
    private fun ZikrCollectionDtoRequest.toDto() = ZikrCollectionDto(
        id = this.id,
        text = this.text,
        isFeatured = this.isFeatured,
        description = this.description,
        orderIndex = this.orderIndex,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
