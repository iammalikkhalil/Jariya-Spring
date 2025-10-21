package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrTagMapRepository
import com.example.demo.presentation.dto.zikr.ZikrTagMapDto
import com.example.demo.presentation.dto.zikr.ZikrTagMapDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrTagMapService(
    private val zikrTagMapRepository: ZikrTagMapRepository
) {

    fun getAllZikrTagMaps() = zikrTagMapRepository.getAllZikrTagMaps()

    fun getZikrTagMapById(id: String) = zikrTagMapRepository.getZikrTagMapById(id)

    fun createZikrTagMap(body: ZikrTagMapDtoRequest): Boolean {
        val dto = ZikrTagMapDto(
            id = body.id,
            zikrId = body.zikrId,
            tagId = body.tagId,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrTagMapRepository.createZikrTagMap(dto.toDomain())
    }

    fun updateZikrTagMap(body: ZikrTagMapDtoRequest): Boolean {
        val dto = ZikrTagMapDto(
            id = body.id,
            zikrId = body.zikrId,
            tagId = body.tagId,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrTagMapRepository.updateZikrTagMap(dto.toDomain())
    }

    fun deleteZikrTagMap(id: String) = zikrTagMapRepository.deleteZikrTagMap(id)

    fun getUpdatedZikrTagMaps(updatedAt: Instant) =
        zikrTagMapRepository.getUpdatedZikrTagMaps(updatedAt)
}
