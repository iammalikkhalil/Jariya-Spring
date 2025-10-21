package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrQualityRepository
import com.example.demo.presentation.dto.zikr.ZikrQualityDto
import com.example.demo.presentation.dto.zikr.ZikrQualityDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrQualityService(
    private val zikrQualityRepository: ZikrQualityRepository
) {

    fun getAllZikrQualities() = zikrQualityRepository.getAllZikrQualities()

    fun getZikrQualityById(id: String) = zikrQualityRepository.getZikrQualityById(id)

    fun createZikrQuality(body: ZikrQualityDtoRequest): Boolean {
        val dto = ZikrQualityDto(
            id = body.id,
            zikrId = body.zikrId,
            text = body.text,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrQualityRepository.createZikrQuality(dto.toDomain())
    }

    fun updateZikrQuality(body: ZikrQualityDtoRequest): Boolean {
        val dto = ZikrQualityDto(
            id = body.id,
            zikrId = body.zikrId,
            text = body.text,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrQualityRepository.updateZikrQuality(dto.toDomain())
    }

    fun deleteZikrQuality(id: String) = zikrQualityRepository.deleteZikrQuality(id)

    fun getUpdatedZikrQualities(updatedAt: Instant) =
        zikrQualityRepository.getUpdatedZikrQualities(updatedAt)
}
