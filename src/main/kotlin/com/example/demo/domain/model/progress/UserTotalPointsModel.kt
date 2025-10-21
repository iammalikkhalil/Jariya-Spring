package com.example.demo.domain.model.progress

import com.example.demo.presentation.dto.progress.UserTotalPointsDto
import java.time.Instant


data class UserTotalPointsModel (
    val id: String,
    val total: Int,
    val isDeleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
)


fun UserTotalPointsModel.toDto()  = UserTotalPointsDto(
    id = this.id,
    total = this.total,
    isDeleted = this.isDeleted,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
)