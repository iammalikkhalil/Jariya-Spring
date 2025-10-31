package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrTranslationJpaRepository
import com.example.demo.domain.model.zikr.ZikrTranslationModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrTranslationRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.system.measureTimeMillis

@Repository
class ZikrTranslationRepositoryImpl(
    private val zikrTranslationJpaRepository: ZikrTranslationJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrTranslationRepository {

    // ✅ Fully optimized eager fetch
    @Transactional(readOnly = true)
    override fun getAllZikrTranslations(): List<ZikrTranslationModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching all ZikrTranslations (JOIN FETCH)...")

        lateinit var result: List<ZikrTranslationModel>
        val dbTime = measureTimeMillis {
            val entities = zikrTranslationJpaRepository.findAllActive()
            result = entities.asSequence().map { it.toModel() }.toList()
        }

        Log.info("✅ getAllZikrTranslations completed in ${System.currentTimeMillis() - start}ms (DB=$dbTime ms)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrTranslationById(id: String): ZikrTranslationModel? {
        return zikrTranslationJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrTranslation(zikrTranslation: ZikrTranslationModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrTranslation.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrTranslation.zikrId}")
                return false
            }

            zikrTranslationJpaRepository.save(zikrTranslation.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_translation")
            Log.info("✅ Created ZikrTranslation: ${zikrTranslation.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrTranslation(zikrTranslation: ZikrTranslationModel): Boolean {
        return try {
            if (!zikrTranslationJpaRepository.existsById(zikrTranslation.id.toUUID())) return false

            val zikrEntity = zikrJpaRepository.findById(zikrTranslation.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrTranslation.zikrId}")
                return false
            }

            zikrTranslationJpaRepository.save(zikrTranslation.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_translation")
            Log.info("✅ Updated ZikrTranslation: ${zikrTranslation.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrTranslation(id: String): Boolean {
        return try {
            val deleted = zikrTranslationJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_translation")
                Log.info("🗑 Soft-deleted ZikrTranslation: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrTranslations(updatedAt: Instant): List<ZikrTranslationModel> {
        val start = System.currentTimeMillis()
        val result = zikrTranslationJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrTranslations fetched ${result.size} records in ${System.currentTimeMillis() - start}ms")
        return result
    }
}
