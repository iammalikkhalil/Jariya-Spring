package com.example.demo.presentation.service.zikr

import com.example.demo.domain.repository.zikr.ZikrTagRepository
import com.example.demo.presentation.dto.zikr.ZikrTagDto
import com.example.demo.presentation.dto.zikr.ZikrTagDtoRequest
import com.example.demo.presentation.dto.zikr.toDomain
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ZikrTagService(
    private val zikrTagRepository: ZikrTagRepository
) {

    fun getAllZikrTags() = zikrTagRepository.getAllZikrTags()

    fun getZikrTagById(id: String) = zikrTagRepository.getZikrTagById(id)

    fun createZikrTag(body: ZikrTagDtoRequest): Boolean {
        val dto = ZikrTagDto(
            id = body.id,
            text = body.text,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrTagRepository.createZikrTag(dto.toDomain())
    }

    fun updateZikrTag(body: ZikrTagDtoRequest): Boolean {
        val dto = ZikrTagDto(
            id = body.id,
            text = body.text,
            createdAt = body.createdAt,
            updatedAt = body.updatedAt,
            isDeleted = body.isDeleted,
            deletedAt = body.deletedAt
        )
        return zikrTagRepository.updateZikrTag(dto.toDomain())
    }

    fun deleteZikrTag(id: String) = zikrTagRepository.deleteZikrTag(id)

    fun getUpdatedZikrTags(updatedAt: Instant) =
        zikrTagRepository.getUpdatedZikrTags(updatedAt)
}
