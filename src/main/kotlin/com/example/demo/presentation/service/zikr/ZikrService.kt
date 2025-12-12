package com.example.demo.presentation.service.zikr

import com.example.demo.domain.model.zikr.BulkInsertResponse
import com.example.demo.domain.model.zikr.SuccessfulDetail
import com.example.demo.domain.repository.zikr.ZikrRepository
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.*
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrService(
    private val zikrRepository: ZikrRepository
) {

    fun getAllZikrs() =
        zikrRepository.getAllZikrs()

    fun getZikrById(id: String) =
        zikrRepository.getZikrById(id)


    // ------------------------------------------------------
    // CREATE — generate ID only if body.id is null
    // ------------------------------------------------------
    fun createZikr(body: CreateZikrDto): ZikrDto? {
        val dto = body.toDtoForCreate()
        return if (zikrRepository.createZikr(dto.toDomain())) dto else null
    }


    // ------------------------------------------------------
    // UPDATE — never generate ID here
    // ------------------------------------------------------
    fun updateZikr(body: CreateZikrDto): Boolean {
        val dto = body.toDtoForUpdate()
        return zikrRepository.updateZikr(dto.toDomain())
    }


    fun deleteZikr(id: String) =
        zikrRepository.deleteZikr(id)


    // ------------------------------------------------------
    // BULK INSERT
    // ------------------------------------------------------
    fun bulkInsertZikrs(rows: List<CsvZikrDto>): BulkInsertResponse {
        val successList = zikrRepository.bulkInsertZikrs(rows)

        return if (successList.isNotEmpty()) {
            BulkInsertResponse(
                message = "Bulk insert completed with partial success",
                totalEntries = rows.size,
                successfulEntries = successList.size,
                failedEntries = rows.size - successList.size,
                successfulDetails = successList.map { (index, id) ->
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


    fun getUpdatedZikrs(updatedAt: Instant) =
        zikrRepository.getUpdatedZikrs(updatedAt)


    // ======================================================
    // PRIVATE HELPERS — DTO Builders
    // ======================================================

    private fun CreateZikrDto.toDtoForCreate() = ZikrDto(
        id = this.id ?: generateUUID(),
        textAr = this.textAr,
        transliteration = this.transliteration,
        quantityNotes = this.quantityNotes,
        sourceNotes = this.sourceNotes,
        isQuran = this.isQuran ?: false,
        isHadith = this.isHadith ?: false,
        isVerified = this.isVerified ?: false,
        verifiedByName = this.verifiedByName,
        verifiedDate = this.verifiedDate,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted ?: false,
        deletedAt = this.deletedAt,
        charCount = this.charCount,
        titleEn = this.titleEn,
        titleUr = this.titleUr
    )

    private fun CreateZikrDto.toDtoForUpdate() = ZikrDto(
        id = this.id!!, // UPDATE MUST HAVE ID
        textAr = this.textAr,
        transliteration = this.transliteration,
        quantityNotes = this.quantityNotes,
        sourceNotes = this.sourceNotes,
        isQuran = this.isQuran ?: false,
        isHadith = this.isHadith ?: false,
        isVerified = this.isVerified ?: false,
        verifiedByName = this.verifiedByName,
        verifiedDate = this.verifiedDate,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = Instant.now(), // force new updatedAt
        isDeleted = this.isDeleted ?: false,
        deletedAt = this.deletedAt,
        charCount = this.charCount,
        titleEn = this.titleEn,
        titleUr = this.titleUr
    )
}
