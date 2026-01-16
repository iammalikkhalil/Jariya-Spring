package com.example.demo.presentation.service.websockets

import com.example.demo.domain.repository.auth.UserRepository
import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.infrastructure.utils.Log
import org.springframework.stereotype.Service

@Service
class StatsCalculationService(
    private val userRepository: UserRepository,
    private val zikrPointRepository: ZikrPointRepository,
    private val cache: StatsCacheService
) {

    /* ===================== GLOBAL ===================== */

    fun recalculateTotalUsers() {
        val totalUsers = userRepository.countAllActiveVerifiedUsers()

        Log.info("__________________________________________________________________")
        Log.info("total users: $totalUsers")
        Log.info("__________________________________________________________________")


        cache.updateTotalUsers(totalUsers)
    }

    fun recalculateTotalHasanats() {
        val totalHasanats = zikrPointRepository.getLeaderboard().total.toLong()
        cache.updateTotalHasanats(totalHasanats)
    }

    fun recalculateGlobalStats() {
        recalculateTotalUsers()
        recalculateTotalHasanats()
    }

    /* ===================== GOALS ===================== */

    fun recalculateGoalsStatsPerGoal() {
        val goalsStats = zikrPointRepository.getStatsByGoalId()
        cache.updateGoalsStats(goalsStats)
    }

    fun recalculateTotalGoalsUsers() {
        val totalUsers = zikrPointRepository.countTotalUsers()
        cache.updateTotalGoalsUsers(totalUsers)
    }

    fun recalculateGoalsStats(){
        recalculateGoalsStatsPerGoal()
        recalculateTotalGoalsUsers()
        recalculateTotalHasanats()
    }




    fun recalculateAll() {
        recalculateGlobalStats()
        recalculateGoalsStats()
    }

}
