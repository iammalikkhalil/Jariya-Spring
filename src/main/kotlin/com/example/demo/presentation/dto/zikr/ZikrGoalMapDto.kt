package com.example.demo.presentation.dto.zikr

import com.example.demo.domain.enums.zikr.GoalCountType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.time.Instant

data class ZikrGoalMapDto(

    val id: String? = null,

    @field:NotBlank(message = "Zikr ID is required")
    val zikrId: String,

    @field:NotBlank(message = "Goal ID is required")
    val goalId: String,

    @field:NotNull(message = "Count type is required")
    val countType: GoalCountType = GoalCountType.TIMES,

    @field:Positive(message = "Count value must be greater than zero")
    @field:Min(value = 1, message = "Count value must be at least 1")
    val countValue: Int = 1,

    @field:Min(value = 0, message = "Order index cannot be negative")
    val orderIndex: Int = 0,

    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,

    val isDeleted: Boolean = false,
    val deletedAt: Instant? = null
)
