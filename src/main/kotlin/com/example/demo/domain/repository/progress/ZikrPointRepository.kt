package com.example.demo.domain.repository.progress

import LeaderboardModel
import com.example.demo.domain.model.progress.ZikrPointModel
import com.example.demo.domain.model.progress.ZikrPointSummaryModel
import com.example.demo.presentation.dto.progress.LeaderboardDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.dto.sync.ZikrPointSyncDto
import com.example.demo.presentation.dto.websockets.GoalStatsDto


interface ZikrPointRepository {
     fun getAllZikrPoints(): List<ZikrPointModel>
     fun getZikrPointById(id: String): ZikrPointModel?
     fun createZikrPoint(zikrPoint: ZikrPointModel): Boolean
     fun updateZikrPoint(zikrPoint: ZikrPointModel): Boolean
     fun deleteZikrPoint(id: String): Boolean
     fun pointExists(progressId: String, parentId: String, currentLevel: Int): Boolean

     fun getZikrPointsSummary(userId: String): ZikrPointSummaryModel
     fun getZikrPointsTotal(): ZikrPointSummaryModel

     fun countTotalUsers(): Long

     fun getLeaderboard(): LeaderboardModel

     fun getStatsByGoalId(): List<GoalStatsDto>

     fun getZikrLeaderboard(): LeaderboardModel

     // ✅ NEW: bulk persist (client-truth)
     fun bulkPersistFromClient(
          items: List<ZikrPointSyncDto>
     ): ZikrPointBulkSyncResponseDto

}
