package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrTagJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrTagMapJpaRepository
import com.example.demo.domain.model.zikr.ZikrTagMapModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrTagMapRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.system.measureTimeMillis

@Repository
class ZikrTagMapRepositoryImpl(
    private val zikrTagMapJpaRepository: ZikrTagMapJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrTagJpaRepository: ZikrTagJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrTagMapRepository {

    // ✅ Fully eager fetch for speed
    @Transactional(readOnly = true)
    override fun getAllZikrTagMaps(): List<ZikrTagMapModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching all ZikrTagMaps (JOIN FETCH)...")

        lateinit var result: List<ZikrTagMapModel>
        val dbTime = measureTimeMillis {
            val entities = zikrTagMapJpaRepository.findAllActive()
            result = entities.asSequence().map { it.toModel() }.toList()
        }

        Log.info("✅ getAllZikrTagMaps completed in ${System.currentTimeMillis() - start}ms (DB=$dbTime ms)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrTagMapById(id: String): ZikrTagMapModel? {
        return zikrTagMapJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrTagMap(zikrTagMap: ZikrTagMapModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrTagMap.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrTagMap.zikrId}")
                return false
            }

            val tagEntity = zikrTagJpaRepository.findById(zikrTagMap.tagId.toUUID()).orElse(null)
            if (tagEntity == null) {
                Log.warn("⚠️ Tag not found for ID ${zikrTagMap.tagId}")
                return false
            }

            zikrTagMapJpaRepository.save(zikrTagMap.toEntity(zikrEntity, tagEntity))
            syncLogRepository.updateSyncLog("zikr_tag_map")
            Log.info("✅ Created ZikrTagMap: ${zikrTagMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrTagMap: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrTagMap(zikrTagMap: ZikrTagMapModel): Boolean {
        return try {
            if (!zikrTagMapJpaRepository.existsById(zikrTagMap.id.toUUID())) return false

            val zikrEntity = zikrJpaRepository.findById(zikrTagMap.zikrId.toUUID()).orElse(null)
            if (zikrEntity == null) {
                Log.warn("⚠️ Zikr not found for ID ${zikrTagMap.zikrId}")
                return false
            }

            val tagEntity = zikrTagJpaRepository.findById(zikrTagMap.tagId.toUUID()).orElse(null)
            if (tagEntity == null) {
                Log.warn("⚠️ Tag not found for ID ${zikrTagMap.tagId}")
                return false
            }

            zikrTagMapJpaRepository.save(zikrTagMap.toEntity(zikrEntity, tagEntity))
            syncLogRepository.updateSyncLog("zikr_tag_map")
            Log.info("✅ Updated ZikrTagMap: ${zikrTagMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrTagMap: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrTagMap(id: String): Boolean {
        return try {
            val deleted = zikrTagMapJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_tag_map")
                Log.info("🗑 Soft-deleted ZikrTagMap: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrTagMap: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrTagMaps(updatedAt: Instant): List<ZikrTagMapModel> {
        val start = System.currentTimeMillis()
        val result = zikrTagMapJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrTagMaps fetched ${result.size} records in ${System.currentTimeMillis() - start}ms")
        return result
    }
}
