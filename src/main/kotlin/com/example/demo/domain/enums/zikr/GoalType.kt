package com.example.demo.domain.enums.zikr

import com.fasterxml.jackson.annotation.JsonValue

enum class GoalType(
    @JsonValue val value: String
) {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    YEARLY("yearly"),
    LIFETIME("lifetime"),
    CUSTOM("custom");

    companion object {
        private val map = entries.associateBy(GoalType::value)

        @JvmStatic
        fun fromString(value: String): GoalType? =
            map[value.trim().lowercase()]

        @JvmStatic
        fun fromStringOrThrow(value: String): GoalType =
            fromString(value) ?: throw IllegalArgumentException(
                "Invalid GoalType: $value. Valid values are: ${
                    entries.joinToString { it.value }
                }"
            )
    }
}