package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrRewardModel
import java.time.Instant


interface ZikrRewardRepository {
     fun getAllZikrRewards(): List<ZikrRewardModel>
     fun getZikrRewardById(id: String): ZikrRewardModel?
     fun createZikrReward(zikrReward: ZikrRewardModel): Boolean
     fun updateZikrReward(zikrReward: ZikrRewardModel): Boolean
     fun deleteZikrReward(id: String): Boolean

     fun getUpdatedZikrRewards(updatedAt: Instant): List<ZikrRewardModel>
}