package com.example.demo.domain.enums.websockets

enum class StatsTopic(val destination: String) {
    GLOBAL("/topic/stats/global"),
    GOALS("/topic/stats/goals"),
    COLLECTIONS("/topic/stats/collections"),
    ZIKRS("/topic/stats/zikrs");

    companion object {
        fun fromDestination(destination: String?): StatsTopic? =
            values().firstOrNull { it.destination == destination }
    }
}
