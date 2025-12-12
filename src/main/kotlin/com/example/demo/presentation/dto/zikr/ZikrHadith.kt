package com.example.demo.presentation.dto.zikr

import com.example.demo.domain.model.zikr.ZikrHadithModel
import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class ZikrHadithDto(

    val id: String,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "textAr is required")
    val textAr: String,

    @field:NotBlank(message = "reference is required")
    val reference: String,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant? = null
)

fun ZikrHadithDto.toDomain() = ZikrHadithModel(
    id = this.id,
    zikrId = this.zikrId,
    textAr = this.textAr,
    reference = this.reference,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
