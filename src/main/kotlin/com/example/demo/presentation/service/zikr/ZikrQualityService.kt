package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrQualityRepository
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.*
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrQualityService(
    private val zikrQualityRepository: ZikrQualityRepository
) {

    fun getAllZikrQualities() =
        zikrQualityRepository.getAllZikrQualities()

    fun getZikrQualityById(id: String) =
        zikrQualityRepository.getZikrQualityById(id)

    fun createZikrQuality(body: ZikrQualityDtoRequest): Boolean =
        zikrQualityRepository.createZikrQuality(
            body.toDto().toDomain()
        )

    fun updateZikrQuality(body: ZikrQualityDtoRequest): Boolean =
        zikrQualityRepository.updateZikrQuality(
            body.toDto().toDomain()
        )

    fun deleteZikrQuality(id: String) =
        zikrQualityRepository.deleteZikrQuality(id)

    fun getUpdatedZikrQualities(updatedAt: Instant) =
        zikrQualityRepository.getUpdatedZikrQualities(updatedAt)


    // ------------------------------------------------------------
    // 🔹 PRIVATE MAPPER (Request -> DTO)
    // ------------------------------------------------------------
    private fun ZikrQualityDtoRequest.toDto() = ZikrQualityDto(
        id = this.id ?: generateUUID(),
        zikrId = this.zikrId,
        text = this.text,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
