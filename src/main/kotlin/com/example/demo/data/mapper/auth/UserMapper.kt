package com.example.demo.data.mapper.auth


import com.example.demo.data.entity.UserEntity
import com.example.demo.domain.enums.AuthProvider
import com.example.demo.domain.model.auth.UserModel
import com.example.demo.presentation.dto.auth.UserDto
import java.util.*

fun UserEntity.toModel(): UserModel = UserModel(
    id = this.id.toString(),
    name = this.name,
    email = this.email,
    password = this.password,
    profileImage = this.profileImage,
    referralCode = this.referralCode,
    referredBy = this.referredBy?.toString(),
    authProvider = AuthProvider.fromValue(this.authProvider),
    isVerified = this.isVerified,
    isDeleted = this.isDeleted,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt
)

fun UserModel.toEntity(): UserEntity = UserEntity(
    id = UUID.fromString(this.id),
    name = this.name,
    email = this.email,
    password = this.password,
    profileImage = this.profileImage,
    referralCode = this.referralCode,
    referredBy = this.referredBy?.let { UUID.fromString(it) },
    authProvider = this.authProvider.value,
    isVerified = this.isVerified,
    isDeleted = this.isDeleted,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt
)



fun UserModel.toDto(): UserDto = UserDto(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    profileImage = this.profileImage,
    referralCode = this.referralCode,
    referredBy = this.referredBy,
    token = null,
    authProvider = this.authProvider,
    isVerified = this.isVerified,
    isDeleted = this.isDeleted,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt
)

fun UserDto.toModel(): UserModel = UserModel(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    profileImage = this.profileImage,
    referralCode = this.referralCode,
    referredBy = this.referredBy,
    authProvider = this.authProvider,
    isVerified = this.isVerified,
    isDeleted = this.isDeleted,
    isActive = this.isActive,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt
)
