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

    fun getAllZikrCollections() = zikrCollectionRepository.getAllZikrCollections()

    fun getZikrCollectionById(id: String) = zikrCollectionRepository.getZikrCollectionById(id)

    fun createZikrCollection(body: ZikrCollectionDtoRequest): Boolean {
        val dto = ZikrCollectionDto(
            id = body.id,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            text = body.text,
            isFeatured = body.isFeatured,
            description = body.description,
            orderIndex = body.orderIndex
        )
        return zikrCollectionRepository.createZikrCollection(dto.toDomain())
    }

    fun updateZikrCollection(body: ZikrCollectionDtoRequest): Boolean {
        val dto = ZikrCollectionDto(
            id = body.id,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            text = body.text,
            isFeatured = body.isFeatured,
            description = body.description,
            orderIndex = body.orderIndex
        )
        return zikrCollectionRepository.updateZikrCollection(dto.toDomain())
    }

    fun deleteZikrCollection(id: String) = zikrCollectionRepository.deleteZikrCollection(id)

    fun getUpdatedZikrCollections(updatedAt: Instant) =
        zikrCollectionRepository.getUpdatedZikrCollections(updatedAt)
}