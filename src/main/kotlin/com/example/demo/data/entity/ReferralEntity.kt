package com.example.demo.data.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "referral",
    indexes = [
        Index(name = "idx_referral_referrer", columnList = "referrer_id"),
        Index(name = "idx_referral_referred", columnList = "referred_id"),
        Index(name = "idx_referral_deleted", columnList = "is_deleted"),
        Index(name = "idx_referral_code_used", columnList = "referral_code_used")
    ]
)
class ReferralEntity(

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referrer_id", referencedColumnName = "id", nullable = false)
    var referrer: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_id", referencedColumnName = "id", nullable = false)
    var referred: UserEntity,

    @Column(name = "referral_code_used", nullable = false, length = 200)
    var referralCodeUsed: String,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null
)
