package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrTranslationRepository
import com.example.demo.presentation.dto.zikr.ZikrTranslationDto
import com.example.demo.presentation.dto.zikr.ZikrTranslationDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrTranslationService(
    private val zikrTranslationRepository: ZikrTranslationRepository
) {

    fun getAllZikrTranslations() = zikrTranslationRepository.getAllZikrTranslations()

    fun getZikrTranslationById(id: String) = zikrTranslationRepository.getZikrTranslationById(id)

    fun createZikrTranslation(body: ZikrTranslationDtoRequest): Boolean {
        val dto = ZikrTranslationDto(
            id = body.id,
            zikrId = body.zikrId,
            translation = body.translation,
            languageCode = body.languageCode,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrTranslationRepository.createZikrTranslation(dto.toDomain())
    }

    fun updateZikrTranslation(body: ZikrTranslationDtoRequest): Boolean {
        val dto = ZikrTranslationDto(
            id = body.id,
            zikrId = body.zikrId,
            translation = body.translation,
            languageCode = body.languageCode,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrTranslationRepository.updateZikrTranslation(dto.toDomain())
    }

    fun deleteZikrTranslation(id: String) = zikrTranslationRepository.deleteZikrTranslation(id)

    fun getUpdatedZikrTranslations(updatedAt: Instant) =
        zikrTranslationRepository.getUpdatedZikrTranslations(updatedAt)
}
