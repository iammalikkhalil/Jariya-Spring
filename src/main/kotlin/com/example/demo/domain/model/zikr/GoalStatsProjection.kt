package com.example.demo.domain.model.zikr

interface GoalStatsProjection {
    fun getGoalId(): String
    fun getEarnedHasanats(): Long
}