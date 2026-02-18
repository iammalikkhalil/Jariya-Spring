package com.example.demo.domain.repository.progress

import com.example.demo.domain.model.progress.ZikrProgressModel
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.dto.sync.ZikrProgressSyncDto


interface ZikrProgressRepository {
     fun getAllZikrProgresses(): List<ZikrProgressModel>
     fun getZikrProgressById(id: String): ZikrProgressModel?
     fun createZikrProgress(zikrProgress: ZikrProgressModel): Boolean
     fun updateZikrProgress(zikrProgress: ZikrProgressModel): Boolean
     fun deleteZikrProgress(id: String): Boolean
     fun getUncompletedRecords(): List<ZikrProgressModel>
     fun incrementZikrProgress(id: String, level: Int): Boolean
     fun markZikrProgressAsComplete(id: String): Boolean

     // ✅ NEW — bulk client-truth persist
     fun bulkPersistFromClient(
          items: List<ZikrProgressSyncDto>
     ): ZikrPointBulkSyncResponseDto
}