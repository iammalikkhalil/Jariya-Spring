package com.example.demo.domain.model.auth

import com.example.demo.domain.enums.AuthProvider
import java.time.Instant

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val password: String?,
    val referredBy: String?,
    val referralCode: String,
    val profileImage: String?,
    val authProvider: AuthProvider,
    val isVerified: Boolean = false,
    val isActive: Boolean = true,
    val isDeleted: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null
)
