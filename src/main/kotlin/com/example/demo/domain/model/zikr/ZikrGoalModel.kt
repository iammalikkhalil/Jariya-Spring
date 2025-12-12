package com.example.demo.domain.model.zikr

import com.example.demo.domain.enums.zikr.GoalSourceType
import com.example.demo.domain.enums.zikr.GoalType
import java.time.Instant

data class ZikrGoalModel(

    val id: String,

    val title: String,
    val description: String?,
    val arabicText: String?,

    val type: GoalType?,
    val targetValue: Int,
    val unit: String?,

    val sourceType: GoalSourceType?,
    val sourceRef: String?,
    val verifiedBy: String?,

    val showFrom: Instant?,
    val showUntil: Instant?,

    val isRecurring: Boolean,
    val recurrence: String?,

    val isFeatured: Boolean,
    val orderIndex: Int,

    val createdAt: Instant,
    val updatedAt: Instant,

    val isDeleted: Boolean,
    val deletedAt: Instant?
)
