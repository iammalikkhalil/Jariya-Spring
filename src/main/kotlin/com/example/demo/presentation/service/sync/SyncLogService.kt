package com.example.demo.presentation.service.sync

import com.example.demo.domain.repository.sync.SyncLogRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class SyncLogService(
    private val syncLogRepository: SyncLogRepository
) {

    fun getAllSyncLogs() = syncLogRepository.getAllSyncLogs()

    fun getUpdatedSyncLogs(updatedAt: Instant) =
        syncLogRepository.getUpdatedSyncLogs(updatedAt)

    fun markSyncLogUpdated(): Boolean =
        syncLogRepository.updateSyncLog("sync")
}
