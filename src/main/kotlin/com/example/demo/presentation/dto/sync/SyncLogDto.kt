package com.example.demo.presentation.dto.sync

import java.time.Instant



data class SyncLogDto(
    val tName: String,
    val updatedAt: Instant,
)