package com.example.demo.domain.model.progress

import com.example.demo.presentation.dto.progress.ZikrProgressDto
import java.time.Instant


data class ZikrProgressModel (
    val id: String,
    val userId: String,
    val zikrId: String,
    val deviceId: String?,
    val sessionId: String?,
    val source: String?,
    val count: Int,
    val processedLevels: Int?,
    val isStarted: Boolean,
    val isCompleted: Boolean,
    val isDeleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
    val syncedAt: Instant? = null,
)



fun ZikrProgressModel.toDto()  = ZikrProgressDto(
    id = this.id,
    userId = this.userId,
    zikrId = this.zikrId,
    count = this.count,
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