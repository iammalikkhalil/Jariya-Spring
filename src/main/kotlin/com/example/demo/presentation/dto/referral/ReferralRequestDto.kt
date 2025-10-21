package com.example.demo.presentation.dto.referral




data class AddReferralRequestDto(
    val userId: String,
    val referralCode: String,
)



data class GetReferralTreeRequestDto(
    val userId: String,
)
