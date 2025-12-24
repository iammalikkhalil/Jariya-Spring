package com.example.demo.presentation.dto.progress

import com.example.demo.domain.model.progress.GoalProgressModel
import java.time.Instant

import com.example.demo.domain.model.progress.ZikrProgressModel



data class GoalProgressDto (
    val id: String,
    val userId: String,
    val zikrId: String,
    val goalId: String,
    val deviceId: String?,
    val sessionId: String?,
    val type: String?,
    val count: Int,
    val charCount: Int,
    val processedLevels: Int?,
    val isStarted: Boolean,
    val isCompleted: Boolean,
    val isDeleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
    val syncedAt: Instant? = null,
)


fun GoalProgressDto.toDomain()  = GoalProgressModel(
    id = this.id,
    userId = this.userId,
    zikrId = this.zikrId,
    goalId = this.goalId,
    count = this.count,
    charCount = this.charCount,
    deviceId = this.deviceId,
    sessionId = this.sessionId,
    type = this.type,
    processedLevels = this.processedLevels,
    isStarted = this.isStarted,
    syncedAt = this.syncedAt,
    isCompleted = this.isCompleted,
    isDeleted = this.isDeleted,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
)