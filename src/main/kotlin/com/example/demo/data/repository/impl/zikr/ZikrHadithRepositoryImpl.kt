package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrHadithJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.model.zikr.ZikrHadithModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrHadithRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrHadithRepositoryImpl(
    private val zikrHadithJpaRepository: ZikrHadithJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrHadithRepository {

    override fun getAllZikrHadiths(): List<ZikrHadithModel> =
        zikrHadithJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrHadithById(id: String): ZikrHadithModel? =
        zikrHadithJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrHadith(zikrHadith: ZikrHadithModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrHadith.zikrId.toUUID()).orElse(null) ?: return false
            zikrHadithJpaRepository.save(zikrHadith.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_hadith")
            Log.info("✅ Created ZikrHadith: ${zikrHadith.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrHadith: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrHadith(zikrHadith: ZikrHadithModel): Boolean {
        return try {
            if (!zikrHadithJpaRepository.existsById(zikrHadith.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.findById(zikrHadith.zikrId.toUUID()).orElse(null) ?: return false
            zikrHadithJpaRepository.save(zikrHadith.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_hadith")
            Log.info("✅ Updated ZikrHadith: ${zikrHadith.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrHadith: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrHadith(id: String): Boolean {
        return try {
            val deleted = zikrHadithJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_hadith")
                Log.info("✅ Soft-deleted ZikrHadith: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrHadith: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrHadiths(updatedAt: Instant): List<ZikrHadithModel> =
        zikrHadithJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}
