package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrHadithTranslationRepository
import com.example.demo.presentation.dto.zikr.ZikrHadithTranslationDto
import com.example.demo.presentation.dto.zikr.ZikrHadithTranslationDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrHadithTranslationService(
    private val zikrHadithTranslationRepository: ZikrHadithTranslationRepository
) {

    fun getAllZikrHadithTranslations() = zikrHadithTranslationRepository.getAllZikrHadithTranslations()

    fun getZikrHadithTranslationById(id: String) = zikrHadithTranslationRepository.getZikrHadithTranslationById(id)

    fun createZikrHadithTranslation(body: ZikrHadithTranslationDtoRequest): Boolean {
        val dto = ZikrHadithTranslationDto(
            id = body.id,
            hadithId = body.hadithId,
            languageCode = body.languageCode,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            translation = body.translation
        )
        return zikrHadithTranslationRepository.createZikrHadithTranslation(dto.toDomain())
    }

    fun updateZikrHadithTranslation(body: ZikrHadithTranslationDtoRequest): Boolean {
        val dto = ZikrHadithTranslationDto(
            id = body.id,
            hadithId = body.hadithId,
            languageCode = body.languageCode,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt,
            translation = body.translation
        )
        return zikrHadithTranslationRepository.updateZikrHadithTranslation(dto.toDomain())
    }

    fun deleteZikrHadithTranslation(id: String) = zikrHadithTranslationRepository.deleteZikrHadithTranslation(id)

    fun getUpdatedZikrHadithTranslations(updatedAt: Instant) =
        zikrHadithTranslationRepository.getUpdatedZikrHadithTranslations(updatedAt)
}
