package com.example.demo.domain.enums.sync

import com.fasterxml.jackson.annotation.JsonValue



enum class SyncStatus(
    @JsonValue val value: String
) {
    UPDATED("updated"),
    PENDING("pending"),
    FAILED("failed");

    companion object {
        private val map = entries.associateBy { it.value }

        @JvmStatic
        fun fromString(value: String): SyncStatus? =
            map[value.trim().lowercase()]

        @JvmStatic
        fun fromStringOrThrow(value: String): SyncStatus =
            fromString(value) ?: throw IllegalArgumentException(
                "Invalid SyncStatus: $value. Valid values are: ${
                    entries.joinToString { it.value }
                }"
            )
    }
}
