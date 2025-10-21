package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrRewardModel


data class ZikrRewardDto (
    val id: String,

    val zikrId: String,
    val text: String,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)



fun ZikrRewardDto.toDomain() = ZikrRewardModel(
    id = this.id,
    zikrId = this.zikrId,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
