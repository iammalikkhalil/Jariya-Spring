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

@Repository
class ZikrTagMapRepositoryImpl(
    private val zikrTagMapJpaRepository: ZikrTagMapJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrTagJpaRepository: ZikrTagJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrTagMapRepository {

    override fun getAllZikrTagMaps(): List<ZikrTagMapModel> =
        zikrTagMapJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrTagMapById(id: String): ZikrTagMapModel? =
        zikrTagMapJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrTagMap(zikrTagMap: ZikrTagMapModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrTagMap.zikrId.toUUID()).orElse(null) ?: return false
            val tagEntity = zikrTagJpaRepository.findById(zikrTagMap.tagId.toUUID()).orElse(null) ?: return false
            zikrTagMapJpaRepository.save(zikrTagMap.toEntity(zikrEntity, tagEntity))
            syncLogRepository.updateSyncLog("zikr_tag_map")
            Log.info("✅ Created ZikrTagMap: ${zikrTagMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrTagMap: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrTagMap(zikrTagMap: ZikrTagMapModel): Boolean {
        return try {
            if (!zikrTagMapJpaRepository.existsById(zikrTagMap.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.findById(zikrTagMap.zikrId.toUUID()).orElse(null) ?: return false
            val tagEntity = zikrTagJpaRepository.findById(zikrTagMap.tagId.toUUID()).orElse(null) ?: return false
            zikrTagMapJpaRepository.save(zikrTagMap.toEntity(zikrEntity, tagEntity))
            syncLogRepository.updateSyncLog("zikr_tag_map")
            Log.info("✅ Updated ZikrTagMap: ${zikrTagMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrTagMap: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrTagMap(id: String): Boolean {
        return try {
            val deleted = zikrTagMapJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_tag_map")
                Log.info("✅ Soft-deleted ZikrTagMap: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrTagMap: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrTagMaps(updatedAt: Instant): List<ZikrTagMapModel> =
        zikrTagMapJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}
