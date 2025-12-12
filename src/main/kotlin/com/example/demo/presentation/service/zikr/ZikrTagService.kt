package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrTagRepository
import com.example.demo.presentation.dto.zikr.ZikrTagDto
import com.example.demo.presentation.dto.zikr.ZikrTagDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import com.example.demo.infrastructure.utils.generateUUID
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrTagService(
    private val zikrTagRepository: ZikrTagRepository
) {

    fun getAllZikrTags() =
        zikrTagRepository.getAllZikrTags()

    fun getZikrTagById(id: String) =
        zikrTagRepository.getZikrTagById(id)


    // ------------------------------------------------------
    // CREATE
    // ------------------------------------------------------
    fun createZikrTag(body: ZikrTagDtoRequest): Boolean {
        val dto = body.toDtoForCreate()
        return zikrTagRepository.createZikrTag(dto.toDomain())
    }


    // ------------------------------------------------------
    // UPDATE (ID must exist)
    // ------------------------------------------------------
    fun updateZikrTag(body: ZikrTagDtoRequest): Boolean {
        val dto = body.toDtoForUpdate()
        return zikrTagRepository.updateZikrTag(dto.toDomain())
    }


    fun deleteZikrTag(id: String) =
        zikrTagRepository.deleteZikrTag(id)


    fun getUpdatedZikrTags(updatedAt: Instant) =
        zikrTagRepository.getUpdatedZikrTags(updatedAt)


    // ======================================================
    // PRIVATE DTO BUILDERS
    // ======================================================

    private fun ZikrTagDtoRequest.toDtoForCreate() = ZikrTagDto(
        id = this.id ?: generateUUID(),
        text = this.text,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )

    private fun ZikrTagDtoRequest.toDtoForUpdate() = ZikrTagDto(
        id = this.id!!, // update ALWAYS requires ID
        text = this.text,
        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = Instant.now(),  // force update timestamp
        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
}
