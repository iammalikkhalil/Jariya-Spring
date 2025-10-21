package com.example.demo.domain.repository.auth


import com.example.demo.domain.model.auth.UserModel

interface UserRepository {
    fun getAllUsers(): List<UserModel>
    fun getParentByChildId(id: String): UserModel?
    fun findUserByEmail(email: String): UserModel?
    fun resetPassword(userId: String, newPassword: String): Boolean
    fun findUserById(id: String): UserModel?
    fun registerUser(user: UserModel): Boolean
    fun updateUser(user: UserModel): Boolean
    fun verifyUser(email: String): Boolean
    fun isVerifiedUser(email: String): Boolean
    fun isAlreadyReferred(userId: String): Boolean
    fun markUserAsVerified(userId: String): Boolean
    fun getUserByReferralCode(referralCode: String): UserModel?
    fun isAlreadyParent(child: String, parent: String): Boolean

}
