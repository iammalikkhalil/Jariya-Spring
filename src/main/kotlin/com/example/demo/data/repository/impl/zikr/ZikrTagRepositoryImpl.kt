package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrTagJpaRepository
import com.example.demo.domain.model.zikr.ZikrTagModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrTagRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrTagRepositoryImpl(
    private val zikrTagJpaRepository: ZikrTagJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrTagRepository {

    override fun getAllZikrTags(): List<ZikrTagModel> =
        zikrTagJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrTagById(id: String): ZikrTagModel? =
        zikrTagJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrTag(zikrTag: ZikrTagModel): Boolean {
        return try {
            zikrTagJpaRepository.save(zikrTag.toEntity())
            syncLogRepository.updateSyncLog("zikr_tag")
            Log.info("✅ Created ZikrTag: ${zikrTag.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrTag: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrTag(zikrTag: ZikrTagModel): Boolean {
        return try {
            if (!zikrTagJpaRepository.existsById(zikrTag.id.toUUID())) return false
            zikrTagJpaRepository.save(zikrTag.toEntity())
            syncLogRepository.updateSyncLog("zikr_tag")
            Log.info("✅ Updated ZikrTag: ${zikrTag.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrTag: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrTag(id: String): Boolean {
        return try {
            val deleted = zikrTagJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_tag")
                Log.info("✅ Soft-deleted ZikrTag: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrTag: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrTags(updatedAt: Instant): List<ZikrTagModel> =
        zikrTagJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}