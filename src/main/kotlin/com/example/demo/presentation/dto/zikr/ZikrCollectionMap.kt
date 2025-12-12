package com.example.demo.presentation.dto.zikr

import com.example.demo.domain.enums.zikr.GoalCountType
import com.example.demo.domain.model.zikr.ZikrCollectionMapModel
import com.example.demo.infrastructure.utils.generateUUID
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant


data class ZikrCollectionMapDto(

    val id: String? = null,   // allow null for create

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "collectionId is required")
    val collectionId: String,

    @field:NotBlank(message = "countType is required")
    val countType: String,   // will be converted to Enum safely

    @field:NotNull(message = "countValue is required")
    val countValue: Int,

    @field:NotNull(message = "orderIndex is required")
    val orderIndex: Int,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,

    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)

fun ZikrCollectionMapDto.toDomain(): ZikrCollectionMapModel =
    ZikrCollectionMapModel(
        id = this.id ?: generateUUID(),

        zikrId = this.zikrId,
        collectionId = this.collectionId,


        countType = this.countType,
        countValue = this.countValue,
        orderIndex = this.orderIndex,

        createdAt = this.createdAt ?: Instant.now(),
        updatedAt = this.updatedAt ?: Instant.now(),

        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
