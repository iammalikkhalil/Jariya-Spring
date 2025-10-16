package com.example.demo.infrastructure.utils


import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

// Instant -> LocalDateTime (UTC)
fun Instant.toLocalDateTimeUTC(): LocalDateTime =
    this.atZone(ZoneOffset.UTC).toLocalDateTime()

// LocalDateTime -> Instant (UTC)
fun LocalDateTime.toInstantUTC(): Instant =
    this.toInstant(ZoneOffset.UTC)
