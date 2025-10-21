package com.example.demo.domain.model.sync

import java.time.Instant


data class SyncLogModel (
    val tName: String,
    val updatedAt: Instant,
)