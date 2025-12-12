package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrTagMapRepository
import com.example.demo.presentation.dto.zikr.ZikrTagMapDto
import com.example.demo.presentation.dto.zikr.ZikrTagMapDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import com.example.demo.infrastructure.utils.generateUUID
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrTagMapService(
    private val zikrTagMapRepository: ZikrTagMapRepository
) {

    fun getAllZikrTagMaps() =
        zikrTagMapRepository.getAllZikrTagMaps()

    fun getZikrTagMapById(id: String) =
        zikrTagMapRepository.getZikrTagMapById(id)


    // ------------------------------------------------------
    // CREATE
    // ------------------------------------------------------
    fun createZikrTagMap(body: ZikrTagMapDtoRequest): Boolean {
        val dto = body.toDtoForCreate()
        return zikrTagMapRepository.createZikrTagMap(dto.toDomain())
    }


    // ------------------------------------------------------
    // UPDATE — ID required
    // ------------------------------------------------------
    fun updateZikrTagMap(body: ZikrTagMapDtoRequest): Boolean {
        val dto = body.toDtoForUpdate()
        return zikrTagMapRepository.updateZikrTagMap(dto.toDomain())
    }


    fun deleteZikrTagMap(id: String) =
        zikrTagMapRepository.deleteZikrTagMap(id)


    fun getUpdatedZikrTagMaps(updatedAt: Instant) =
        zikrTagMapRepository.getUpdatedZikrTagMaps(updatedAt)


    // ======================================================
    // PRIVATE DTO BUILDERS
    // ======================================================

    private fun ZikrTagMapDtoRequest.toDtoForCreate() = ZikrTagMapDto(
        id = this.id ?: generateUUID(),
        zikrId = this.zikrId,
        tagId = this.tagId,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )

    private fun ZikrTagMapDtoRequest.toDtoForUpdate() = ZikrTagMapDto(
        id = this.id!!, // update MUST require ID
        zikrId = this.zikrId,
        tagId = this.tagId,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = Instant.now(), // force update timestamp
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
