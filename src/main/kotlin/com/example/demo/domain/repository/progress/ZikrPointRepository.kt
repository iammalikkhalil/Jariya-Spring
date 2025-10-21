package com.example.demo.domain.repository.progress

import LeaderboardModel
import com.example.demo.domain.model.progress.ZikrPointModel
import com.example.demo.domain.model.progress.ZikrPointSummaryModel
import com.example.demo.presentation.dto.progress.LeaderboardDto


interface ZikrPointRepository {
     fun getAllZikrPoints(): List<ZikrPointModel>
     fun getZikrPointById(id: String): ZikrPointModel?
     fun createZikrPoint(zikrPoint: ZikrPointModel): Boolean
     fun updateZikrPoint(zikrPoint: ZikrPointModel): Boolean
     fun deleteZikrPoint(id: String): Boolean
     fun pointExists(progressId: String, parentId: String, currentLevel: Int): Boolean

     fun getZikrPointsSummary(userId: String): ZikrPointSummaryModel
     fun getZikrPointsTotal(): ZikrPointSummaryModel

     fun getLeaderboard(): LeaderboardModel

     fun getZikrLeaderboard(): LeaderboardModel
}
