package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrQualityJpaRepository
import com.example.demo.domain.model.zikr.ZikrQualityModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrQualityRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrQualityRepositoryImpl(
    private val zikrQualityJpaRepository: ZikrQualityJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrQualityRepository {

    override fun getAllZikrQualities(): List<ZikrQualityModel> =
        zikrQualityJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrQualityById(id: String): ZikrQualityModel? =
        zikrQualityJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrQuality(zikrQuality: ZikrQualityModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrQuality.zikrId.toUUID()).orElse(null) ?: return false
            zikrQualityJpaRepository.save(zikrQuality.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_quality")
            Log.info("✅ Created ZikrQuality: ${zikrQuality.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrQuality: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrQuality(zikrQuality: ZikrQualityModel): Boolean {
        return try {
            if (!zikrQualityJpaRepository.existsById(zikrQuality.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.findById(zikrQuality.zikrId.toUUID()).orElse(null) ?: return false
            zikrQualityJpaRepository.save(zikrQuality.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_quality")
            Log.info("✅ Updated ZikrQuality: ${zikrQuality.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrQuality: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrQuality(id: String): Boolean {
        return try {
            val deleted = zikrQualityJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_quality")
                Log.info("✅ Soft-deleted ZikrQuality: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrQuality: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrQualities(updatedAt: Instant): List<ZikrQualityModel> =
        zikrQualityJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}
