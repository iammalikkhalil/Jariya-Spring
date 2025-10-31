package com.example.demo.domain.model.auth

import com.example.demo.domain.enums.AuthProvider
import com.fasterxml.jackson.annotation.JsonInclude
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



@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val password: String? = null,
    val profileImage: String? = null,
    val referredBy: String? = null,
    val referralCode: String,
    val authProvider: AuthProvider = AuthProvider.USERNAME_PASSWORD,
    val token: String? = null,
    val isVerified: Boolean = false,
    val isDeleted: Boolean = false,
    val isActive: Boolean = true,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null
)