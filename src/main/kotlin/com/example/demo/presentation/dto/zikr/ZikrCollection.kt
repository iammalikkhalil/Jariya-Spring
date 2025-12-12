package com.example.demo.presentation.dto.zikr

import com.example.demo.domain.model.zikr.ZikrCollectionModel
import com.example.demo.infrastructure.utils.generateUUID
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant


data class ZikrCollectionDto(

    val id: String? = null,   // FE may send null for new creation

    @field:NotBlank(message = "text is required")
    val text: String,

    @field:NotNull(message = "isFeatured is required")
    val isFeatured: Boolean,

    val description: String? = null,

    @field:NotNull(message = "orderIndex is required")
    val orderIndex: Int,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,

    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)


fun ZikrCollectionDto.toDomain(): ZikrCollectionModel =
    ZikrCollectionModel(
        id = this.id ?: generateUUID(),   // SAFE: avoids accidental new-row bugs

        text = this.text,
        isFeatured = this.isFeatured,
        description = this.description,
        orderIndex = this.orderIndex,

        createdAt = this.createdAt ?: Instant.now(), // guarantees non-null
        updatedAt = this.updatedAt ?: Instant.now(), // guarantees non-null

        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
