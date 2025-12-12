package com.example.demo.presentation.dto.zikr

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant
import com.example.demo.domain.model.zikr.ZikrQualityModel


data class ZikrQualityDto(

    @field:NotBlank(message = "id is required")
    val id: String,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "text is required")
    @field:Size(max = 5000, message = "text is too long")
    val text: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant? = null
)


fun ZikrQualityDto.toDomain() = ZikrQualityModel(
    id = this.id,
    zikrId = this.zikrId,
    text = this.text.trim(),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
