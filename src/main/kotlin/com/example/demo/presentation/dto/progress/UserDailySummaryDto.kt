package com.example.demo.presentation.dto.progress


import java.time.Instant
import com.example.demo.domain.model.progress.UserDailySummaryModel



data class UserDailySummaryDto (
    val id: String,
    val userId: String,
    val zikrCount: Int,
    val points: Int,
    val date: Instant,
    val isDeleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
)


fun UserDailySummaryDto.toDomain()  = UserDailySummaryModel(
    id = this.id,
    userId = this.userId,
    zikrCount = this.zikrCount,
    points = this.points,
    date = this.date,
    isDeleted = this.isDeleted,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
)