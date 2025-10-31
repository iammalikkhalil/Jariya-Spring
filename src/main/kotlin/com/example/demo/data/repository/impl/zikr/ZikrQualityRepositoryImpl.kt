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
import kotlin.system.measureTimeMillis

@Repository
class ZikrQualityRepositoryImpl(
    private val zikrQualityJpaRepository: ZikrQualityJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrQualityRepository {

    // ✅ Fast eager query — single select with JOIN FETCH
    @Transactional(readOnly = true)
    override fun getAllZikrQualities(): List<ZikrQualityModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching all ZikrQualities (JOIN FETCH)...")

        lateinit var result: List<ZikrQualityModel>
        val dbTime = measureTimeMillis {
            val entities = zikrQualityJpaRepository.findAllActive()
            result = entities.asSequence().map { it.toModel() }.toList()
        }

        Log.info("✅ getAllZikrQualities completed in ${System.currentTimeMillis() - start}ms (DB=$dbTime ms)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrQualityById(id: String): ZikrQualityModel? {
        return zikrQualityJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrQuality(zikrQuality: ZikrQualityModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrQuality.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrQuality.zikrId}")
                return false
            }

            zikrQualityJpaRepository.save(zikrQuality.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_quality")
            Log.info("✅ Created ZikrQuality: ${zikrQuality.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrQuality: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrQuality(zikrQuality: ZikrQualityModel): Boolean {
        return try {
            if (!zikrQualityJpaRepository.existsById(zikrQuality.id.toUUID())) return false

            val zikrEntity = zikrJpaRepository.findById(zikrQuality.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrQuality.zikrId}")
                return false
            }

            zikrQualityJpaRepository.save(zikrQuality.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_quality")
            Log.info("✅ Updated ZikrQuality: ${zikrQuality.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrQuality: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrQuality(id: String): Boolean {
        return try {
            val deleted = zikrQualityJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_quality")
                Log.info("🗑 Soft-deleted ZikrQuality: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrQuality: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrQualities(updatedAt: Instant): List<ZikrQualityModel> {
        val start = System.currentTimeMillis()
        val result = zikrQualityJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrQualities fetched ${result.size} records in ${System.currentTimeMillis() - start}ms")
        return result
    }
}
