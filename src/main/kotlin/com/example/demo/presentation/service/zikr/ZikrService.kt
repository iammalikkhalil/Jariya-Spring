package com.example.demo.presentation.service.zikr

import com.example.demo.domain.model.zikr.BulkInsertResponse
import com.example.demo.domain.model.zikr.SuccessfulDetail
import com.example.demo.domain.repository.zikr.ZikrRepository
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.CreateZikrDto
import com.example.demo.presentation.dto.zikr.CsvZikrDto
import com.example.demo.presentation.dto.zikr.ZikrDto
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.Instant.now

@Service
class ZikrService(
    private val zikrRepository: ZikrRepository
) {

    fun getAllZikrs() = zikrRepository.getAllZikrs()

    fun getZikrById(id: String) = zikrRepository.getZikrById(id)

    fun createZikr(body: CreateZikrDto): ZikrDto? {
        val dto = ZikrDto(
            id = body.id ?: generateUUID(),
            textAr = body.textAr,
            transliteration = body.transliteration,
            quantityNotes = body.quantityNotes,
            sourceNotes = body.sourceNotes,
            isQuran = body.isQuran ?: false,
            isHadith = body.isHadith ?: false,
            isVerified = body.isVerified ?: false,
            verifiedByName = body.verifiedByName,
            verifiedDate = body.verifiedDate,
            createdAt = body.createdAt ?: now(),
            updatedAt = body.updatedAt ?: now(),
            isDeleted = body.isDeleted ?: false,
            deletedAt = body.deletedAt,
            charCount = body.charCount,
            titleEn = body.titleEn,
            titleUr = body.titleUr
        )

        return if (zikrRepository.createZikr(dto.toDomain())) dto else null
    }

    fun updateZikr(body: CreateZikrDto): Boolean {
        val dto = ZikrDto(
            id = body.id ?: generateUUID(),
            textAr = body.textAr,
            transliteration = body.transliteration,
            quantityNotes = body.quantityNotes,
            sourceNotes = body.sourceNotes,
            isQuran = body.isQuran ?: false,
            isHadith = body.isHadith ?: false,
            isVerified = body.isVerified ?: false,
            verifiedByName = body.verifiedByName,
            verifiedDate = body.verifiedDate,
            createdAt = body.createdAt ?: now(),
            updatedAt = body.updatedAt ?: now(),
            isDeleted = body.isDeleted ?: false,
            deletedAt = body.deletedAt,
            charCount = body.charCount,
            titleEn = body.titleEn,
            titleUr = body.titleUr
        )
        return zikrRepository.updateZikr(dto.toDomain())
    }

    fun deleteZikr(id: String) = zikrRepository.deleteZikr(id)

    fun bulkInsertZikrs(rows: List<CsvZikrDto>): BulkInsertResponse {
        val successfulEntries = zikrRepository.bulkInsertZikrs(rows)

        return if (successfulEntries.isNotEmpty()) {
            BulkInsertResponse(
                message = "Bulk insert completed with partial success",
                totalEntries = rows.size,
                successfulEntries = successfulEntries.size,
                failedEntries = rows.size - successfulEntries.size,
                successfulDetails = successfulEntries.map { (index, id) ->
                    SuccessfulDetail(index, id.toString())
                }
            )
        } else {
            BulkInsertResponse(
                message = "All entries failed to insert",
                totalEntries = rows.size,
                successfulEntries = 0,
                failedEntries = rows.size
            )
        }
    }

    fun getUpdatedZikrs(updatedAt: Instant) = zikrRepository.getUpdatedZikrs(updatedAt)
}
