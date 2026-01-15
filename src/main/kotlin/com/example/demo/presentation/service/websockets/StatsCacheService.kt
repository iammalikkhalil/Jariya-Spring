package com.example.demo.presentation.service.websockets

import com.example.demo.presentation.dto.websockets.GoalStatsDto
import org.springframework.stereotype.Service

@Service
class StatsCacheService{

    @Volatile private var totalUsers: Long? = null
    @Volatile private var totalGoalsUsers: Long? = null
    @Volatile private var totalHasanats: Long? = null
    @Volatile private var goalsStats:  List<GoalStatsDto> = emptyList()

    /* ===================== GLOBAL ===================== */

    fun getTotalUsers(): Long =
        totalUsers ?: 0L

    fun updateTotalUsers(value: Long) {
        totalUsers = value
    }

    fun getTotalHasanats(): Long =
        totalHasanats ?: 0L

    fun updateTotalHasanats(value: Long) {
        totalHasanats = value
    }

    /* ===================== GOALS ===================== */


    fun getTotalGoalsUsers(): Long =
        totalGoalsUsers ?: 0L

    fun updateTotalGoalsUsers(value: Long) {
        totalGoalsUsers = value
    }

    fun getGoalsStats(): List<GoalStatsDto> =
        goalsStats

    fun updateGoalsStats(value: List<GoalStatsDto>) {
        goalsStats = value
    }

    /* ===================== CLEAR ===================== */

    fun clearAll() {
        totalUsers = null
        totalHasanats = null
        goalsStats = emptyList()
    }
}