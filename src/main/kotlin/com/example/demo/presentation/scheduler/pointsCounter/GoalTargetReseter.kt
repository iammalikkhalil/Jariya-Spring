package com.example.demo.presentation.scheduler.pointsCounter

import com.example.demo.presentation.service.progress.ZikrPointService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class GoalTargetRecalculator(
    private val zikrPointService: ZikrPointService
) {

    private val log = LoggerFactory.getLogger(GoalTargetRecalculator::class.java)

    @Scheduled(fixedRate = 1 * 60 * 1000) // every 1 hour
    fun recalculateGoalTargets() {

        val start = System.currentTimeMillis()
        log.info("⏱ Starting Goal Target recalculation job...")

        try {

            zikrPointService.recalculateAndUpdateGoalGlobalTargetValues()

            log.info(
                "✅ Goal Target recalculation completed in ${System.currentTimeMillis() - start} ms"
            )

        } catch (ex: Exception) {

            log.error(
                "❌ Goal Target recalculation job failed",
                ex
            )
        }
    }
}
