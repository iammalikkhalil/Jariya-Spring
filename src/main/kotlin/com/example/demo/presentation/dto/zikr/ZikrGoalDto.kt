package com.example.demo.presentation.dto.zikr

import com.example.demo.domain.enums.zikr.GoalSourceType
import com.example.demo.domain.enums.zikr.GoalType
import java.time.Instant
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ZikrGoalDto(

    // Nullable and optional (allows FE and offline sync)
    val id: String? = null,

    @field:NotBlank(message = "Title cannot be empty")
    @field:Size(max = 500, message = "Title cannot exceed 500 characters")
    val title: String,

    @field:Size(max = 2000, message = "Description cannot exceed 2000 characters")
    val description: String? = null,

    @field:Size(max = 2000, message = "Arabic text cannot exceed 2000 characters")
    val arabicText: String? = null,

    // Can be null unless business rule requires it later
    val type: String? = null,

    @field:Min(value = 1, message = "Target value must be at least 1")
    val targetValue: Int = 1,

    @field:Size(max = 50, message = "Unit cannot exceed 50 characters")
    val unit: String? = null,

    val sourceType: String? = null,

    @field:Size(max = 2000, message = "Source reference cannot exceed 2000 characters")
    val sourceRef: String? = null,

    @field:Size(max = 2000, message = "Verified by cannot exceed 2000 characters")
    val verifiedBy: String? = null,

    val showFrom: Instant? = null,
    val showUntil: Instant? = null,

    val isRecurring: Boolean = false,

    @field:Size(max = 1000, message = "Recurrence format is too long")
    val recurrence: String? = null,

    val isFeatured: Boolean = false,

    @field:Min(value = 0, message = "Order index cannot be negative")
    val orderIndex: Int = 0,

    // These are FE-generated in offline flow → allowed to come from request
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,

    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)
