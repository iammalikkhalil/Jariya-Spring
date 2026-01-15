package com.example.demo.presentation.service.websockets


import com.example.demo.domain.events.GoalsStatsChangedEvent
import com.example.demo.domain.events.UserStatsChangedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class WebSocketStatsEventListener(
    private val statsBroadcastService: StatsBroadcastService,
    private val statsCalculationService: StatsCalculationService
) {

    /* ===================== USER ===================== */

    @EventListener
    fun onUserStatsChanged(event: UserStatsChangedEvent) {
        statsCalculationService.recalculateTotalUsers()
        statsBroadcastService.broadcastGlobalStats()
    }

    /* ===================== GOALS ===================== */

    @EventListener
    fun onGoalsStatsChanged(event: GoalsStatsChangedEvent) {
        statsCalculationService.recalculateGoalsStats()
        statsBroadcastService.broadcastGlobalStats()
        statsBroadcastService.broadcastGoalsStats()
    }
}
