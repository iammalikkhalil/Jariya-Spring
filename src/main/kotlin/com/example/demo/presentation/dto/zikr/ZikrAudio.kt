package com.example.demo.presentation.dto.zikr

import java.time.Instant

import com.example.demo.domain.model.zikr.ZikrAudioModel


data class ZikrAudioDto (
    val id: String,

    val zikrId: String,
    val audioUrl: String,
    val languageCode: String,
    val duration: Int,

    val createdAt: Instant,
    val updatedAt: Instant,
    val isDeleted: Boolean,
    val deletedAt: Instant?= null
)

fun ZikrAudioDto.toDomain()= ZikrAudioModel(
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