package com.example.demo.domain.enums


enum class SourceType(val value: String) {
    ZIKR("zikr"),
    REFERRAL("referral"),
    BONUS("bonus"),
    OTHER("other");

    companion object {
        fun fromValue(value: String): SourceType =
            entries.find { it.value == value } ?: OTHER
    }
}
