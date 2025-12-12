package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrTranslationRepository
import com.example.demo.presentation.dto.zikr.ZikrTranslationDto
import com.example.demo.presentation.dto.zikr.ZikrTranslationDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import com.example.demo.infrastructure.utils.generateUUID
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrTranslationService(
    private val zikrTranslationRepository: ZikrTranslationRepository
) {

    fun getAllZikrTranslations() =
        zikrTranslationRepository.getAllZikrTranslations()

    fun getZikrTranslationById(id: String) =
        zikrTranslationRepository.getZikrTranslationById(id)


    // ------------------------------------------------------
    // CREATE
    // ------------------------------------------------------
    fun createZikrTranslation(body: ZikrTranslationDtoRequest): Boolean {
        val dto = body.toDtoForCreate()
        return zikrTranslationRepository.createZikrTranslation(dto.toDomain())
    }


    // ------------------------------------------------------
    // UPDATE (ID must exist)
    // ------------------------------------------------------
    fun updateZikrTranslation(body: ZikrTranslationDtoRequest): Boolean {
        val dto = body.toDtoForUpdate()
        return zikrTranslationRepository.updateZikrTranslation(dto.toDomain())
    }


    fun deleteZikrTranslation(id: String) =
        zikrTranslationRepository.deleteZikrTranslation(id)

    fun getUpdatedZikrTranslations(updatedAt: Instant) =
        zikrTranslationRepository.getUpdatedZikrTranslations(updatedAt)


    // ======================================================
    // PRIVATE HELPERS
    // ======================================================

    private fun ZikrTranslationDtoRequest.toDtoForCreate() = ZikrTranslationDto(
        id = this.id ?: generateUUID(),
        zikrId = this.zikrId,
        translation = this.translation,
        languageCode = this.languageCode,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )

    private fun ZikrTranslationDtoRequest.toDtoForUpdate() = ZikrTranslationDto(
        id = this.id!!, // update ALWAYS requires ID
        zikrId = this.zikrId,
        translation = this.translation,
        languageCode = this.languageCode,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = Instant.now(), // force backend update
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
