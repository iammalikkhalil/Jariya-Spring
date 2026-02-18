package com.example.demo.utils

import java.util.UUID

fun String.toUUIDOrNull(): UUID? =
    try {
        UUID.fromString(this)
    } catch (e: Exception) {
        null
    }
