package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "zikr_points")
data class ZikrPointEntity(

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID = UUID.randomUUID(),

    // FK → user.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val user: UserEntity,

    // FK → zikr.id (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zikr_id", referencedColumnName = "id")
    val zikr: ZikrEntity? = null,

    // FK → progress.id (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id", referencedColumnName = "id")
    val progress: ZikrProgressEntity? = null,

    @Column(name = "level", nullable = false)
    val level: Int,

    @Column(name = "points", nullable = false)
    val points: Int,

    @Column(name = "source_type", nullable = false, columnDefinition = "TEXT")
    val sourceType: String,

    // FK → user.id (source of referral/points)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_user", referencedColumnName = "id", nullable = false)
    val sourceUser: UserEntity,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)
