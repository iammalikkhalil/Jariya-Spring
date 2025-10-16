package com.example.demo.infrastructure.utils

import java.util.UUID

fun generateUUID(): String = UUID.randomUUID().toString()

fun String.toUUID(): UUID = try {
    UUID.fromString(this)
} catch (e: IllegalArgumentException) {
    UUID.randomUUID()
}