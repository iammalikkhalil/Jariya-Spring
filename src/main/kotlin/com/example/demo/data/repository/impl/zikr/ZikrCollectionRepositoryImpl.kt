package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrCollectionJpaRepository
import com.example.demo.domain.model.zikr.ZikrCollectionModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrCollectionRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrCollectionRepositoryImpl(
    private val zikrCollectionJpaRepository: ZikrCollectionJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrCollectionRepository {

    // ✅ Fast eager-loaded read with logging
    @Transactional(readOnly = true)
    override fun getAllZikrCollections(): List<ZikrCollectionModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching ZikrCollections (JOIN FETCH ready)...")

        val result = zikrCollectionJpaRepository.findAllActive().map { it.toModel() }

        Log.info("✅ getAllZikrCollections completed in ${System.currentTimeMillis() - start} ms (${result.size} records)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrCollectionById(id: String): ZikrCollectionModel? {
        return zikrCollectionJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikrCollection(zikrCollection: ZikrCollectionModel): Boolean {
        return try {
            zikrCollectionJpaRepository.save(zikrCollection.toEntity())
            syncLogRepository.updateSyncLog("zikr_collection")
            Log.info("✅ Created ZikrCollection: ${zikrCollection.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrCollection: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikrCollection(zikrCollection: ZikrCollectionModel): Boolean {
        return try {
            if (!zikrCollectionJpaRepository.existsById(zikrCollection.id.toUUID())) return false
            zikrCollectionJpaRepository.save(zikrCollection.toEntity())
            syncLogRepository.updateSyncLog("zikr_collection")
            Log.info("✅ Updated ZikrCollection: ${zikrCollection.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrCollection: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikrCollection(id: String): Boolean {
        return try {
            val deleted = zikrCollectionJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_collection")
                Log.info("🗑 Soft-deleted ZikrCollection: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrCollection: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrCollections(updatedAt: Instant): List<ZikrCollectionModel> {
        val start = System.currentTimeMillis()
        val result = zikrCollectionJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrCollections fetched ${result.size} records in ${System.currentTimeMillis() - start} ms")
        return result
    }
}
