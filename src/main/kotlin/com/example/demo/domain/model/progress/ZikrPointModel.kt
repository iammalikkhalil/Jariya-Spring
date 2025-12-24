package com.example.demo.domain.model.progress

import com.example.demo.domain.enums.PointsSourceType
import com.example.demo.presentation.dto.progress.ZikrPointDto
import java.time.Instant


data class ZikrPointModel(

    val id: String,

    val userId: String,
    val progressType: String,
    val progressId: String,
    val zikrId: String?,

    val level: Int,
    val points: Int,
    val pointsSourceType: PointsSourceType,
    val sourceUser: String,

    val isDeleted: Boolean,

    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant?
)

fun ZikrPointModel.toDto()  = ZikrPointDto(
    id = this.id,
    userId = this.userId,
    zikrId = this.zikrId,
    isDeleted = this.isDeleted,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
    points = this.points,
    pointsSourceType = this.pointsSourceType,
    sourceUser = this.sourceUser,
    progressId = this.progressId,
    progressType = this.progressType,
    level = this.level,
)