package com.example.demo.presentation.dto.referral

import com.example.demo.domain.model.referral.ReferralModel


data class ReferralDto (
    val ancestor: String,
    val descendant: String,
    val level: Int,
)

fun ReferralDto.toDomain() : ReferralModel = ReferralModel(
    ancestor = this.ancestor,
    descendant = this.descendant,
    level = this.level
)


