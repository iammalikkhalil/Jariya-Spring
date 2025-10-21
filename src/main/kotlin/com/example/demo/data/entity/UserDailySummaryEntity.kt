package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "user_daily_summary")
data class UserDailySummaryEntity(

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    // FK → users.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val user: UserEntity,

    @Column(name = "zikr_count", nullable = false)
    val zikrCount: Int,

    @Column(name = "points", nullable = false)
    val points: Int,

    @Column(name = "date", nullable = false)
    val date: Instant,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)
