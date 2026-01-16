package com.example.demo.infrastructure.bootstrap

import com.example.demo.presentation.service.websockets.StatsCalculationService
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class StatsBootstrapper(
    private val statsCalculationService: StatsCalculationService
) {
    @PostConstruct
    fun bootstrap() {
        try {
            statsCalculationService.recalculateAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
