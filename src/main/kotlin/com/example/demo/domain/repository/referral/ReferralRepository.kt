package com.example.demo.domain.repository.referral

import com.example.demo.domain.model.referral.ReferralModel


interface ReferralRepository {
     fun getAllReferrals(): List<ReferralModel>
     fun isAlreadyExists(ancestor: String, descendant: String): Boolean
     fun addReferral(ancestor: String, descendant: String): Boolean
     fun getReferralTreeUp(ancestor: String): List<ReferralModel> // <- Add this
     fun getReferralTreeDown(descendant: String): List<ReferralModel> // <- Add this
}