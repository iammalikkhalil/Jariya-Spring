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

    override fun getAllZikrCollections(): List<ZikrCollectionModel> =
        zikrCollectionJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrCollectionById(id: String): ZikrCollectionModel? =
        zikrCollectionJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
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

    @Transactional
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

    @Transactional
    override fun deleteZikrCollection(id: String): Boolean {
        return try {
            val deleted = zikrCollectionJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_collection")
                Log.info("✅ Soft-deleted ZikrCollection: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrCollection: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrCollections(updatedAt: Instant): List<ZikrCollectionModel> =
        zikrCollectionJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}