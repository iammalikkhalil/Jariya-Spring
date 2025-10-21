package com.example.demo.domain.model.zikr

import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.presentation.dto.zikr.ZikrAudioDto
import java.time.Instant
import java.time.Instant.now


data class ZikrAudioModel (
    val id: String = generateUUID(),

    val zikrId: String,
    val audioUrl: String,
    val languageCode: String,
    val duration: Int,

    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val isDeleted: Boolean = false,
    val deletedAt: Instant?= null
)

fun ZikrAudioModel.toDto()= ZikrAudioDto(
    id = this.id,
    zikrId = this.zikrId,
    audioUrl = this.audioUrl,
    languageCode = this.languageCode,
    duration = this.duration,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    isDeleted = this.isDeleted,
    deletedAt = this.deletedAt
)
