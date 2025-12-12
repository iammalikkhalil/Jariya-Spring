package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "sync_log",
    indexes = [
        Index(name = "idx_sync_log_updated_at", columnList = "updated_at")
    ]
)
class SyncLogEntity(

    @Id
    @Column(name = "table_name", nullable = false, updatable = false, columnDefinition = "TEXT")
    var tName: String,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant
)
