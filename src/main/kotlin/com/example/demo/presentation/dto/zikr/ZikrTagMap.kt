package com.example.demo.presentation.dto.zikr

import jakarta.validation.constraints.NotBlank
import java.time.Instant
import com.example.demo.domain.model.zikr.ZikrTagMapModel


data class ZikrTagMapDto(

    @field:NotBlank(message = "id is required")
    val id: String,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "tagId is required")
    val tagId: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant? = null
)

fun ZikrTagMapDto.toDomain() = ZikrTagMapModel(
    id = this.id,
    zikrId = this.zikrId,
    tagId = this.tagId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
