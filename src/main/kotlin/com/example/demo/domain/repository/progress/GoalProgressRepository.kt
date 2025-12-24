package com.example.demo.domain.repository.progress

import com.example.demo.domain.model.progress.GoalProgressModel


interface GoalProgressRepository {
     fun getAllGoalProgresses(): List<GoalProgressModel>
     fun getGoalProgressById(id: String): GoalProgressModel?
     fun createGoalProgress(goalProgress: GoalProgressModel): Boolean
     fun updateGoalProgress(goalProgress: GoalProgressModel): Boolean
     fun deleteGoalProgress(id: String): Boolean
     fun getUncompletedRecords(): List<GoalProgressModel>
     fun incrementGoalProgress(id: String, level: Int): Boolean
     fun markGoalProgressAsComplete(id: String): Boolean
}