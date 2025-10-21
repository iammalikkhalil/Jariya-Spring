package com.example.demo.presentation.dto.progress

import java.time.Instant

import com.example.demo.domain.model.progress.UserTotalPointsModel



data class UserTotalPointsDto (
    val id: String,
    val total: Int,
    val isDeleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
)


fun UserTotalPointsDto.toDomain()  = UserTotalPointsModel(
    id = this.id,
    total = this.total,
    isDeleted = this.isDeleted,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
)