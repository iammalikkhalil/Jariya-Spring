package com.example.demo.presentation.dto.progress


import com.example.demo.domain.enums.PointsSourceType
import com.example.demo.infrastructure.utils.generateUUID
import jakarta.validation.Valid
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator
import java.time.Instant
import java.time.Instant.now


data class GoalProgressRequestDto (
    val id: String = generateUUID(),
    val userId: String,
    val zikrId: String,
    val goalId: String,
    val deviceId: String?= null,
    val sessionId: String?= null,
    val type: String? = null,
    val count: Int,
    val charCount: Int,
    val processedLevels: Int? = null,
    val isStarted: Boolean = false,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false,
    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val deletedAt: Instant? = null,
    val syncedAt: Instant? = null,
)



data class ZikrProgressRequestDto (
    val id: String = generateUUID(),
    val userId: String,
    val zikrId: String,
    val deviceId: String?= generateUUID(),
    val sessionId: String?= null,
    val source: String? = "manual",
    val count: Int,
    val processedLevels: Int? = null,
    val isStarted: Boolean = false,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false,
    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val deletedAt: Instant? = null,
    val syncedAt: Instant? = null,
)



data class ZikrPointRequestDto (
    val id: String = generateUUID(),
    val userId: String = generateUUID(),
    val zikrId: String = generateUUID(),
    val progressId: String,
    val progressType: String = "zikr",
    val level: Int = 0,
    val points: Int = 100,
    val pointsSourceType: PointsSourceType = PointsSourceType.ZIKR,
    val sourceUser: String = generateUUID(),
    val isDeleted: Boolean = false,
    val createdAt: Instant = now(),
    val updatedAt: Instant = now(),
    val deletedAt: Instant? = null,
)



data class ZikrPointSummaryRequestDto (
    val id: String = generateUUID(),
)








