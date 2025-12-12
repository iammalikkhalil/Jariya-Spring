package com.example.demo.domain.enums

enum class PointsSourceType(val value: String) {
    ZIKR("zikr"),
    REFERRAL("referral"),
    BONUS("bonus"),
    OTHER("other");

    companion object {
        fun fromValue(value: String): PointsSourceType =
            entries.find { it.value == value.trim().lowercase() } ?: OTHER
    }
}