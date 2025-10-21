package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrRewardDto
import java.time.Instant
import java.time.Instant.now


data class ZikrRewardModel (
    val id: String = generateUUID(),

    val zikrId: String,
    val text: String,

    val createdAt: Instant = now(),
    val updatedAt: Instant  = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)




fun ZikrRewardModel.toDto() = ZikrRewardDto(
    id = this.id,
    zikrId = this.zikrId,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)


