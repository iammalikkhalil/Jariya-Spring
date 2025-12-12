package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrHadithRepository
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrHadithDto
import com.example.demo.presentation.dto.zikr.ZikrHadithDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrHadithService(
    private val zikrHadithRepository: ZikrHadithRepository
) {

    fun getAllZikrHadiths() =
        zikrHadithRepository.getAllZikrHadiths()

    fun getZikrHadithById(id: String) =
        zikrHadithRepository.getZikrHadithById(id)

    fun createZikrHadith(body: ZikrHadithDtoRequest): Boolean =
        zikrHadithRepository.createZikrHadith(body.toDto().toDomain())

    fun updateZikrHadith(body: ZikrHadithDtoRequest): Boolean =
        zikrHadithRepository.updateZikrHadith(body.toDto().toDomain())

    fun deleteZikrHadith(id: String) =
        zikrHadithRepository.deleteZikrHadith(id)

    fun getUpdatedZikrHadiths(updatedAt: Instant) =
        zikrHadithRepository.getUpdatedZikrHadiths(updatedAt)



    // ------------------------------------------------------------
    // 🔹 Private clean converter (Request → DTO)
    // ------------------------------------------------------------
    private fun ZikrHadithDtoRequest.toDto() = ZikrHadithDto(
        id = this.id ?: generateUUID(),
        zikrId = this.zikrId,
        textAr = this.textAr,
        reference = this.reference,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
