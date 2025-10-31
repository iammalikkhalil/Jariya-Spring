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
import kotlin.system.measureTimeMillis

@Repository
class ZikrHadithRepositoryImpl(
    private val zikrHadithJpaRepository: ZikrHadithJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrHadithRepository {

    // ✅ Single SQL query (JOIN FETCH) — no N+1
    @Transactional(readOnly = true)
    override fun getAllZikrHadiths(): List<ZikrHadithModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching ZikrHadiths with JOIN FETCH...")

        lateinit var result: List<ZikrHadithModel>
        val dbTime = measureTimeMillis {
            val entities = zikrHadithJpaRepository.findAllActive()
            result = entities.asSequence().map { it.toModel() }.toList()
        }

        Log.info("✅ getAllZikrHadiths completed in ${System.currentTimeMillis() - start}ms (DB=${dbTime}ms, total=${System.currentTimeMillis() - start}ms)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrHadithById(id: String): ZikrHadithModel? {
        return zikrHadithJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrHadith(zikrHadith: ZikrHadithModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrHadith.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrHadith.zikrId}")
                return false
            }

            zikrHadithJpaRepository.save(zikrHadith.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_hadith")
            Log.info("✅ Created ZikrHadith: ${zikrHadith.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrHadith: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrHadith(zikrHadith: ZikrHadithModel): Boolean {
        return try {
            if (!zikrHadithJpaRepository.existsById(zikrHadith.id.toUUID())) return false

            val zikrEntity = zikrJpaRepository.findById(zikrHadith.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrHadith.zikrId}")
                return false
            }

            zikrHadithJpaRepository.save(zikrHadith.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_hadith")
            Log.info("✅ Updated ZikrHadith: ${zikrHadith.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrHadith: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrHadith(id: String): Boolean {
        return try {
            val updated = zikrHadithJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (updated > 0) {
                syncLogRepository.updateSyncLog("zikr_hadith")
                Log.info("🗑 Soft-deleted ZikrHadith: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrHadith: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrHadiths(updatedAt: Instant): List<ZikrHadithModel> {
        val start = System.currentTimeMillis()
        val result = zikrHadithJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrHadiths fetched ${result.size} records in ${System.currentTimeMillis() - start}ms")
        return result
    }
}
