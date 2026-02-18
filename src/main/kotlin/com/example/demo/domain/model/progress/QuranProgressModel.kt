package com.example.demo.domain.model.progress


import com.example.demo.presentation.dto.sync.QuranProgressSyncDto
import java.time.Instant
import kotlin.Int

data class QuranProgressModel(

    val id: String,

    val userId: String,
    val ayahId: Int,
    val surahId: Int? = null,
    val deviceId: String?,
    val sessionId: String?,
    val source: String?,

    val charCount: Int = 1,
    val count: Int,
    val processedLevels: Int?,

    val isStarted: Boolean,
    val isCompleted: Boolean,
    val isDeleted: Boolean,

    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant?,
    val syncedAt: Instant?
)

fun QuranProgressModel.toDto()  = QuranProgressSyncDto(
    id = this.id,
    userId = this.userId,
    ayahId = this.ayahId.toString(),
    surahId = this.surahId?.toString(),
    count = this.count.toString(),
    charCount = this.charCount.toString(),
    deviceId = this.deviceId,
    sessionId = this.sessionId,
    source = this.source,
    processedLevels = this.processedLevels,
    isStarted = this.isStarted,
    syncedAt = this.syncedAt,
    isCompleted = this.isCompleted,
    isDeleted = this.isDeleted,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
)