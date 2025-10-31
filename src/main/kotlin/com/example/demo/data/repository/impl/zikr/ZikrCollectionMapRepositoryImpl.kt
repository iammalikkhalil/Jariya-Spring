package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrCollectionMapJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrCollectionJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.model.zikr.ZikrCollectionMapModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrCollectionMapRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.system.measureTimeMillis

@Repository
class ZikrCollectionMapRepositoryImpl(
    private val zikrCollectionMapJpaRepository: ZikrCollectionMapJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrCollectionJpaRepository: ZikrCollectionJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrCollectionMapRepository {

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getAllZikrCollectionMaps(): List<ZikrCollectionMapModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ [getAllZikrCollectionMaps] Fetching ZikrCollectionMap records (with JOIN FETCH)...")

        lateinit var result: List<ZikrCollectionMapModel>
        var queryTime = 0L
        var mappingTime = 0L

        try {
            queryTime = measureTimeMillis {
                val entities = zikrCollectionMapJpaRepository.findAllActive()
                result = entities.asSequence().map { it.toModel() }.toList()
            }
            mappingTime = System.currentTimeMillis() - start - queryTime
            Log.info("✅ Query & mapping complete in ${queryTime + mappingTime}ms (DB=${queryTime}ms, map=${mappingTime}ms)")
        } catch (e: Exception) {
            Log.error("❌ Error in getAllZikrCollectionMaps(): ${e.message}", e)
            throw e
        }

        Log.info("⏰ getAllZikrCollectionMaps total time = ${System.currentTimeMillis() - start}ms")
        return result
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getZikrCollectionMapById(id: String): ZikrCollectionMapModel? {
        return zikrCollectionMapJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrCollectionMap(zikrCollectionMap: ZikrCollectionMapModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.getReferenceById(zikrCollectionMap.zikrId.toUUID())
            val collectionEntity = zikrCollectionJpaRepository.getReferenceById(zikrCollectionMap.collectionId.toUUID())

            zikrCollectionMapJpaRepository.saveAndFlush(zikrCollectionMap.toEntity(zikrEntity, collectionEntity))
            syncLogRepository.updateSyncLog("zikr_collection_map")

            Log.info("✅ Created ZikrCollectionMap: ${zikrCollectionMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrCollectionMap: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrCollectionMap(zikrCollectionMap: ZikrCollectionMapModel): Boolean {
        return try {
            if (!zikrCollectionMapJpaRepository.existsById(zikrCollectionMap.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.getReferenceById(zikrCollectionMap.zikrId.toUUID())
            val collectionEntity = zikrCollectionJpaRepository.getReferenceById(zikrCollectionMap.collectionId.toUUID())

            zikrCollectionMapJpaRepository.saveAndFlush(zikrCollectionMap.toEntity(zikrEntity, collectionEntity))
            syncLogRepository.updateSyncLog("zikr_collection_map")

            Log.info("✅ Updated ZikrCollectionMap: ${zikrCollectionMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrCollectionMap: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrCollectionMap(id: String): Boolean {
        return try {
            val updated = zikrCollectionMapJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (updated > 0) {
                syncLogRepository.updateSyncLog("zikr_collection_map")
                Log.info("🗑 Soft-deleted ZikrCollectionMap: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrCollectionMap: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getUpdatedZikrCollectionMaps(updatedAt: Instant): List<ZikrCollectionMapModel> {
        return zikrCollectionMapJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
    }
}
