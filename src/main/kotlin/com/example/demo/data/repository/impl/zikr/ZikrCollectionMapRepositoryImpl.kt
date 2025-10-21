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
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrCollectionMapRepositoryImpl(
    private val zikrCollectionMapJpaRepository: ZikrCollectionMapJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrCollectionJpaRepository: ZikrCollectionJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrCollectionMapRepository {

    override fun getAllZikrCollectionMaps(): List<ZikrCollectionMapModel> =
        zikrCollectionMapJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrCollectionMapById(id: String): ZikrCollectionMapModel? =
        zikrCollectionMapJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrCollectionMap(zikrCollectionMap: ZikrCollectionMapModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrCollectionMap.zikrId.toUUID()).orElse(null) ?: return false
            val collectionEntity = zikrCollectionJpaRepository.findById(zikrCollectionMap.collectionId.toUUID()).orElse(null) ?: return false
            zikrCollectionMapJpaRepository.save(zikrCollectionMap.toEntity(zikrEntity, collectionEntity))
            syncLogRepository.updateSyncLog("zikr_collection_map")
            Log.info("✅ Created ZikrCollectionMap: ${zikrCollectionMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrCollectionMap: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrCollectionMap(zikrCollectionMap: ZikrCollectionMapModel): Boolean {
        return try {
            if (!zikrCollectionMapJpaRepository.existsById(zikrCollectionMap.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.findById(zikrCollectionMap.zikrId.toUUID()).orElse(null) ?: return false
            val collectionEntity = zikrCollectionJpaRepository.findById(zikrCollectionMap.collectionId.toUUID()).orElse(null) ?: return false
            zikrCollectionMapJpaRepository.save(zikrCollectionMap.toEntity(zikrEntity, collectionEntity))
            syncLogRepository.updateSyncLog("zikr_collection_map")
            Log.info("✅ Updated ZikrCollectionMap: ${zikrCollectionMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrCollectionMap: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrCollectionMap(id: String): Boolean {
        return try {
            val updated = zikrCollectionMapJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (updated > 0) {
                syncLogRepository.updateSyncLog("zikr_collection_map")
                Log.info("✅ Soft-deleted ZikrCollectionMap: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrCollectionMap: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrCollectionMaps(updatedAt: Instant): List<ZikrCollectionMapModel> =
        zikrCollectionMapJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}