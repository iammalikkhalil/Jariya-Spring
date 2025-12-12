package com.example.demo.presentation.dto.zikr

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant
import com.example.demo.domain.model.zikr.ZikrTagModel


data class ZikrTagDto(

    @field:NotBlank(message = "id is required")
    val id: String,

    @field:NotBlank(message = "text is required")
    @field:Size(max = 255, message = "text must not exceed 255 characters")
    val text: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant? = null
)


fun ZikrTagDto.toDomain() = ZikrTagModel(
    id = this.id,
    text = this.text.trim(),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
