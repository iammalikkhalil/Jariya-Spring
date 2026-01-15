package com.example.demo.domain.repository.progress

import com.example.demo.domain.model.progress.GoalProgressModel
import com.example.demo.presentation.dto.sync.GoalProgressSyncDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto


interface GoalProgressRepository {
     fun getAllGoalProgresses(): List<GoalProgressModel>
     fun getGoalProgressById(id: String): GoalProgressModel?
     fun createGoalProgress(goalProgress: GoalProgressModel): Boolean
     fun updateGoalProgress(goalProgress: GoalProgressModel): Boolean
     fun deleteGoalProgress(id: String): Boolean
     fun getUncompletedRecords(): List<GoalProgressModel>
     fun incrementGoalProgress(id: String, level: Int): Boolean
     fun markGoalProgressAsComplete(id: String): Boolean
     fun countTotalUsers(): Long

     // ✅ NEW — bulk client-truth persist
     fun bulkPersistFromClient(
          items: List<GoalProgressSyncDto>
     ): ZikrPointBulkSyncResponseDto
}