package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrGoalMapModel
import java.time.Instant

interface ZikrGoalMapRepository {

    fun getAllGoalMaps(): List<ZikrGoalMapModel>

    fun getGoalMapById(id: String): ZikrGoalMapModel?

    fun createGoalMap(goalMap: ZikrGoalMapModel): Boolean

    fun updateGoalMap(goalMap: ZikrGoalMapModel): Boolean

    fun deleteGoalMap(id: String): Boolean

    fun getUpdatedGoalMaps(updatedAt: Instant): List<ZikrGoalMapModel>
}
