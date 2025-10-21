package com.example.demo.domain.repository.sync

import com.example.demo.domain.model.sync.SyncLogModel
import java.time.Instant


interface SyncLogRepository {
     fun getAllSyncLogs(): List<SyncLogModel>
     fun getUpdatedSyncLogs(updatedAt: Instant): List<SyncLogModel>
     fun updateSyncLog(tName: String): Boolean
}