package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrTagDto
import java.time.Instant
import java.time.Instant.now


data class ZikrTagModel (
    val id: String = generateUUID(),

    val text: String,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)



fun ZikrTagModel.toDto() = ZikrTagDto(
    id = this.id,
    text = this.text,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)

