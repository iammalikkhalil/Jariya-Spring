package com.example.demo.data.entity


import com.example.demo.domain.enums.AuthProvider
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @Column(nullable = false, unique = true)
    val id: UUID,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = true)
    val password: String? = null,

    @Column(name = "profile_image")
    val profileImage: String? = null,

    @Column(name = "auth_provider", nullable = false)
    val authProvider: String = AuthProvider.USERNAME_PASSWORD.value,

    @Column(name = "referral_code", nullable = false, unique = true, length = 20)
    val referralCode: String,

    @Column(name = "referred_by")
    var referredBy: UUID? = null,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,

    @Column(name = "is_verified", nullable = false)
    val isVerified: Boolean = false,

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Column(name = "deleted_at")
    val deletedAt: Instant? = null
)
