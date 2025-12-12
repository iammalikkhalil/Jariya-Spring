package com.example.demo.domain.enums.zikr

import com.fasterxml.jackson.annotation.JsonValue

enum class GoalSourceType(
    @JsonValue val value: String
) {
    QURAN("quran"),
    HADITH("hadith"),
    SCHOLAR("scholar"),
    CUSTOM("custom");

    companion object {
        private val map = entries.associateBy(GoalSourceType::value)

        @JvmStatic
        fun fromString(value: String): GoalSourceType? =
            map[value.trim().lowercase()]

        @JvmStatic
        fun fromStringOrThrow(value: String): GoalSourceType =
            fromString(value) ?: throw IllegalArgumentException(
                "Invalid GoalSourceType: $value. Valid values are: ${
                    entries.joinToString { it.value }
                }"
            )
    }
}
