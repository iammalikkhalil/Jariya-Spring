package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrHadithTranslationRepository
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.*
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrHadithTranslationService(
    private val zikrHadithTranslationRepository: ZikrHadithTranslationRepository
) {

    fun getAllZikrHadithTranslations() =
        zikrHadithTranslationRepository.getAllZikrHadithTranslations()

    fun getZikrHadithTranslationById(id: String) =
        zikrHadithTranslationRepository.getZikrHadithTranslationById(id)

    fun createZikrHadithTranslation(body: ZikrHadithTranslationDtoRequest): Boolean =
        zikrHadithTranslationRepository.createZikrHadithTranslation(
            body.toDto().toDomain()
        )

    fun updateZikrHadithTranslation(body: ZikrHadithTranslationDtoRequest): Boolean =
        zikrHadithTranslationRepository.updateZikrHadithTranslation(
            body.toDto().toDomain()
        )

    fun deleteZikrHadithTranslation(id: String) =
        zikrHadithTranslationRepository.deleteZikrHadithTranslation(id)

    fun getUpdatedZikrHadithTranslations(updatedAt: Instant) =
        zikrHadithTranslationRepository.getUpdatedZikrHadithTranslations(updatedAt)


    // ------------------------------------------------------------
    // 🔹 PRIVATE CLEAN MAPPER (Request -> DTO)
    // ------------------------------------------------------------
    private fun ZikrHadithTranslationDtoRequest.toDto() = ZikrHadithTranslationDto(
        id = this.id ?: generateUUID(),
        hadithId = this.hadithId,
        translation = this.translation,
        languageCode = this.languageCode,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
