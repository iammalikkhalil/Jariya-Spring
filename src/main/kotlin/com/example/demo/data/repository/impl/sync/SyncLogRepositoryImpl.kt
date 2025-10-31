package com.example.demo.data.repository.impl.sync

import com.example.demo.data.mapper.sync.toEntity
import com.example.demo.data.mapper.sync.toModel
import com.example.demo.data.repository.jpa.sync.SyncLogJpaRepository
import com.example.demo.domain.model.sync.SyncLogModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.Log
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.Instant.now

@Repository
class SyncLogRepositoryImpl(
    private val syncLogJpaRepository: SyncLogJpaRepository
) : SyncLogRepository {

    override fun getAllSyncLogs(): List<SyncLogModel> =
        syncLogJpaRepository.findAll().map { it.toModel() }

    override fun getUpdatedSyncLogs(updatedAt: Instant): List<SyncLogModel> =
        syncLogJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateSyncLog(tName: String): Boolean {
        return try {
            val existing = syncLogJpaRepository.findByTName(tName)
            if (existing != null) {
                existing.updatedAt = now()
                syncLogJpaRepository.save(existing)
                Log.info("✅ Updated SyncLog for table: $tName")
            } else {
                syncLogJpaRepository.save(SyncLogModel(tName = tName, updatedAt = Instant.now()).toEntity())
                Log.info("✅ Created SyncLog entry for new table: $tName")
            }
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating SyncLog for table: $tName — ${e.message}", e)
            false
        }
    }
}
