package com.example.demo.domain.enums.zikr

import com.fasterxml.jackson.annotation.JsonValue

enum class GoalCountType(
    @JsonValue val value: String
) {
    TIMES("times"),
    CHARACTERS("characters"),
    MINUTES("minutes"),
    PAGES("pages"),
    CUSTOM("custom");

    companion object {
        private val map = entries.associateBy { it.value }

        @JvmStatic
        fun fromString(value: String): GoalCountType? =
            map[value.trim().lowercase()]

        @JvmStatic
        fun fromStringOrThrow(value: String): GoalCountType =
            fromString(value) ?: throw IllegalArgumentException(
                "Invalid CountType: $value. Valid values are: ${
                    entries.joinToString { it.value }
                }"
            )
    }
}
