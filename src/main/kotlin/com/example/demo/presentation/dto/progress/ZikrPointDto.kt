package com.example.demo.presentation.dto.progress

import java.time.Instant

import com.example.demo.domain.enums.SourceType
import com.example.demo.domain.model.progress.ZikrPointModel



data class ZikrPointDto (
    val id: String,
    val userId: String,
    val progressId: String?,
    val zikrId: String?,
    val level: Int,
    val points: Int,
    val sourceType: SourceType,
    val sourceUser: String,
    val isDeleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
)


fun ZikrPointDto.toDomain()  = ZikrPointModel(
    id = this.id,
    userId = this.userId,
    zikrId = this.zikrId,
    isDeleted = this.isDeleted,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
    points = this.points,
    sourceType = this.sourceType,
    sourceUser = this.sourceUser,
    progressId = this.progressId,
    level = this.level,
)