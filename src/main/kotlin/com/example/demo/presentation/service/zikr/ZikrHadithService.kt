package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrHadithRepository
import com.example.demo.presentation.dto.zikr.ZikrHadithDto
import com.example.demo.presentation.dto.zikr.ZikrHadithDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrHadithService(
    private val zikrHadithRepository: ZikrHadithRepository
) {

    fun getAllZikrHadiths() = zikrHadithRepository.getAllZikrHadiths()

    fun getZikrHadithById(id: String) = zikrHadithRepository.getZikrHadithById(id)

    fun createZikrHadith(body: ZikrHadithDtoRequest): Boolean {
        val dto = ZikrHadithDto(
            id = body.id,
            zikrId = body.zikrId,
            textAr = body.textAr,
            reference = body.reference,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrHadithRepository.createZikrHadith(dto.toDomain())
    }

    fun updateZikrHadith(body: ZikrHadithDtoRequest): Boolean {
        val dto = ZikrHadithDto(
            id = body.id,
            zikrId = body.zikrId,
            textAr = body.textAr,
            reference = body.reference,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrHadithRepository.updateZikrHadith(dto.toDomain())
    }

    fun deleteZikrHadith(id: String) = zikrHadithRepository.deleteZikrHadith(id)

    fun getUpdatedZikrHadiths(updatedAt: Instant) =
        zikrHadithRepository.getUpdatedZikrHadiths(updatedAt)
}
