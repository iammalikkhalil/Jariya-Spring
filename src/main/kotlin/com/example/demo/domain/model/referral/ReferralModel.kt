package com.example.demo.domain.model.referral

import com.example.demo.presentation.dto.referral.ReferralDto


data class ReferralModel (
    val ancestor: String,
    val descendant: String,
    val level: Int,
)


fun ReferralModel.toDto() : ReferralDto = ReferralDto(
    ancestor = this.ancestor,
    descendant = this.descendant,
    level = this.level
)
