package com.example.demo.presentation.scheduler.websocket

import com.example.demo.presentation.service.websockets.StatsBroadcastService
import com.example.demo.presentation.service.websockets.StatsCalculationService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class StatsWebSocketScheduler(
    private val statsBroadcastService: StatsBroadcastService
) {

    private val log = LoggerFactory.getLogger(StatsWebSocketScheduler::class.java)

    /**
     * Runs every 5 minutes (safety net)
     */
    @Scheduled(fixedRate = 5 * 60 * 1000)
    fun broadcastStatsEvery5Minutes() {
        log.info("⏱ Broadcasting stats every 5 minutes (safety net)")

        statsBroadcastService.broadcastGlobalStats()
        statsBroadcastService.broadcastGoalsStats()
    }
}