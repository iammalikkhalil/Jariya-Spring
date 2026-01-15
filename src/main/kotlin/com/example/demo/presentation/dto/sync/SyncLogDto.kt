package com.example.demo.presentation.dto.sync


import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.time.Instant



data class SyncLogDto(
    val tName: String,
    val updatedAt: Instant,
)


data class ZikrPointBulkSyncRequestDto(
    @field:Size(min = 1, message = "items list must contain at least one item")
    @field:Valid
    val items: List<ZikrPointSyncDto>
)

data class GoalProgressBulkSyncRequestDto(
    @field:Size(min = 1, message = "items list must contain at least one item")
    @field:Valid
    val items: List<GoalProgressSyncDto>
)


data class SyncSummaryDto(
    val syncedAt: Instant = Instant.now(),
    val created: Int = 0,
    val updated: Int = 0,
    val deleted: Int = 0,
    val failed: Int = 0
)

data class SyncAcknowledgeDto(
val id: String,
val status: String = "CREATED", // mock status
    val message: String? = null,
)

data class ZikrPointBulkSyncResponseDto(
val summary: SyncSummaryDto,
val acknowledge: List<SyncAcknowledgeDto>
)

data class ZikrPointSyncDto(

    @field:NotBlank(message = "id is required")
    val id: String,

    @field:NotBlank(message = "userId is required")
    val userId: String,

    @field:NotBlank(message = "progressType is required")
    val progressType: String,

    @field:NotBlank(message = "progressId is required")
    val progressId: String,

    @field:NotBlank(message = "zikrId is required")
    val zikrId: String,

    @field:Positive(message = "points must be greater than 0")
    val points: Int,

    @field:NotBlank(message = "distributionType is required")
    val distributionType: String,

    @field:PositiveOrZero(message = "level must be 0 or greater")
    val level: Int,

    val sourceUserId: String?,

    @field:NotNull(message = "earnedAt is required")
    val earnedAt: Long,

    @field:NotNull(message = "createdAt is required")
    val createdAt: Long,

    val updatedAt: Long?,

    @field:NotNull(message = "isDeleted is required")
    val isDeleted: Boolean,

    @field:NotBlank(message = "syncStatus is required")
    val syncStatus: String
)

data class GoalProgressSyncDto(

    @field:NotBlank(message = "id is required")
    val id: String,

    val userId: String?,

    val zikrId: String?,

    val goalId: String?,

    val sessionId: String?,

    val deviceId: String?,

    val type: String?,

    val countValue: String?,

    val count: String?,

    val charCount: String?,

    val levels: String?,

    val isStarted: String?,

    val isCompleted: String?,

    val syncedAt: String?,

    val createdAt: Long?,

    val updatedAt: Long?,

    val isDeleted: Boolean?,

    val syncStatus: String?
)