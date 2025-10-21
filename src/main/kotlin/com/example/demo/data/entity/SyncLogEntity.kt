package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "sync_log")
data class SyncLogEntity(

    @Id
    @Column(name = "table_name", nullable = false, unique = true, columnDefinition = "TEXT")
    val tName: String,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),
)
