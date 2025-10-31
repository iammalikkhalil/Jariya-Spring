package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrHadithJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrHadithTranslationJpaRepository
import com.example.demo.domain.model.zikr.ZikrHadithTranslationModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrHadithTranslationRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.system.measureTimeMillis

@Repository
class ZikrHadithTranslationRepositoryImpl(
    private val zikrHadithTranslationJpaRepository: ZikrHadithTranslationJpaRepository,
    private val zikrHadithJpaRepository: ZikrHadithJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrHadithTranslationRepository {

    // ✅ Single eager-loaded query
    @Transactional(readOnly = true)
    override fun getAllZikrHadithTranslations(): List<ZikrHadithTranslationModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching ZikrHadithTranslations with JOIN FETCH...")

        lateinit var result: List<ZikrHadithTranslationModel>
        val dbTime = measureTimeMillis {
            val entities = zikrHadithTranslationJpaRepository.findAllActive()
            result = entities.asSequence().map { it.toModel() }.toList()
        }

        Log.info("✅ getAllZikrHadithTranslations completed in ${System.currentTimeMillis() - start}ms (DB=${dbTime}ms)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrHadithTranslationById(id: String): ZikrHadithTranslationModel? {
        return zikrHadithTranslationJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrHadithTranslation(zikrHadithTranslation: ZikrHadithTranslationModel): Boolean {
        return try {
            val hadithEntity = zikrHadithJpaRepository.findById(zikrHadithTranslation.hadithId.toUUID()).orElse(null)
            if (hadithEntity == null) {
                Log.warn("⚠️ Hadith not found for ID ${zikrHadithTranslation.hadithId}")
                return false
            }

            zikrHadithTranslationJpaRepository.save(zikrHadithTranslation.toEntity(hadithEntity))
            syncLogRepository.updateSyncLog("zikr_hadith_translation")
            Log.info("✅ Created ZikrHadithTranslation: ${zikrHadithTranslation.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrHadithTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrHadithTranslation(zikrHadithTranslation: ZikrHadithTranslationModel): Boolean {
        return try {
            if (!zikrHadithTranslationJpaRepository.existsById(zikrHadithTranslation.id.toUUID())) return false

            val hadithEntity = zikrHadithJpaRepository.findById(zikrHadithTranslation.hadithId.toUUID()).orElse(null)
            if (hadithEntity == null) {
                Log.warn("⚠️ Hadith not found for ID ${zikrHadithTranslation.hadithId}")
                return false
            }

            zikrHadithTranslationJpaRepository.save(zikrHadithTranslation.toEntity(hadithEntity))
            syncLogRepository.updateSyncLog("zikr_hadith_translation")
            Log.info("✅ Updated ZikrHadithTranslation: ${zikrHadithTranslation.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrHadithTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrHadithTranslation(id: String): Boolean {
        return try {
            val deleted = zikrHadithTranslationJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_hadith_translation")
                Log.info("🗑 Soft-deleted ZikrHadithTranslation: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrHadithTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrHadithTranslations(updatedAt: Instant): List<ZikrHadithTranslationModel> {
        val start = System.currentTimeMillis()
        val result = zikrHadithTranslationJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrHadithTranslations fetched ${result.size} records in ${System.currentTimeMillis() - start}ms")
        return result
    }
}
