package com.example.demo.domain.repository.progress

import com.example.demo.domain.model.progress.QuranProgressModel
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.dto.sync.QuranProgressSyncDto


interface QuranProgressRepository {
     fun getAllQuranProgresses(): List<QuranProgressModel>
     fun getQuranProgressById(id: String): QuranProgressModel?
     fun createQuranProgress(quranProgress: QuranProgressModel): Boolean
     fun updateQuranProgress(quranProgress: QuranProgressModel): Boolean
     fun deleteQuranProgress(id: String): Boolean
     fun getUncompletedRecords(): List<QuranProgressModel>
     fun incrementQuranProgress(id: String, level: Int): Boolean
     fun markQuranProgressAsComplete(id: String): Boolean

     // ✅ NEW — bulk client-truth persist
     fun bulkPersistFromClient(
          items: List<QuranProgressSyncDto>
     ): ZikrPointBulkSyncResponseDto
}