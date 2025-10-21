package com.example.demo.domain.model.referral

sealed class ReferralResult {
    abstract val message: String

    data class Success(val referralPathCreated: Boolean = true) : ReferralResult() {
        override val message: String = "Referral added successfully"
    }

    object AlreadyReferred : ReferralResult() {
        override val message: String = "Referral already exists"
    }

    object InvalidReferralCode : ReferralResult() {
        override val message: String = "Invalid referral code"
    }

    object SelfReferralNotAllowed : ReferralResult() {
        override val message: String = "You cannot refer yourself"
    }

    object InternalError : ReferralResult() {
        override val message: String = "Something went wrong. Please try again later"
    }
}
