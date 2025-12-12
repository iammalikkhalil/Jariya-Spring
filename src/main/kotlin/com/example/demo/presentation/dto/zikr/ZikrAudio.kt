package com.example.demo.presentation.dto.zikr

import com.example.demo.domain.model.zikr.ZikrAudioModel
import com.example.demo.infrastructure.utils.generateUUID
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant


data class ZikrAudioDto(

    val id: String? = null,  // FE may send null → backend generates UUID

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:NotBlank(message = "Audio URL is required")
    val audioUrl: String,

    @field:NotBlank(message = "Language code is required")
    val languageCode: String,

    @field:NotNull(message = "Duration is required")
    val duration: Int,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,

    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)

fun ZikrAudioDto.toDomain(): ZikrAudioModel =
    ZikrAudioModel(
        id = this.id ?: generateUUID(),   // FIX: prevents accidental new row creation

        zikrId = this.zikrId,
        audioUrl = this.audioUrl,
        languageCode = this.languageCode,
        duration = this.duration,

        createdAt = this.createdAt ?: Instant.now(),  // FIX: always non-null
        updatedAt = this.updatedAt ?: Instant.now(),  // FIX: always non-null

        isDeleted = this.isDeleted,
        deletedAt = this.deletedAt
    )
