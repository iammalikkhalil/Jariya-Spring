package com.example.demo.domain.repository.zikr

import com.example.demo.domain.model.zikr.ZikrGoalModel
import java.time.Instant

interface ZikrGoalRepository {

    fun getAllGoals(): List<ZikrGoalModel>

    fun getGoalById(id: String): ZikrGoalModel?

    fun createGoal(goal: ZikrGoalModel): Boolean

    fun updateGoal(goal: ZikrGoalModel): Boolean

    fun deleteGoal(id: String): Boolean

    fun getUpdatedGoals(updatedAt: Instant): List<ZikrGoalModel>
}
