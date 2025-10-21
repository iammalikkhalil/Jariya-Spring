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

@Repository
class ZikrTranslationRepositoryImpl(
    private val zikrTranslationJpaRepository: ZikrTranslationJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrTranslationRepository {

    override fun getAllZikrTranslations(): List<ZikrTranslationModel> =
        zikrTranslationJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrTranslationById(id: String): ZikrTranslationModel? =
        zikrTranslationJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrTranslation(zikrTranslation: ZikrTranslationModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrTranslation.zikrId.toUUID()).orElse(null) ?: return false
            zikrTranslationJpaRepository.save(zikrTranslation.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_translation")
            Log.info("✅ Created ZikrTranslation: ${zikrTranslation.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrTranslation(zikrTranslation: ZikrTranslationModel): Boolean {
        return try {
            if (!zikrTranslationJpaRepository.existsById(zikrTranslation.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.findById(zikrTranslation.zikrId.toUUID()).orElse(null) ?: return false
            zikrTranslationJpaRepository.save(zikrTranslation.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_translation")
            Log.info("✅ Updated ZikrTranslation: ${zikrTranslation.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrTranslation: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrTranslation(id: String): Boolean {
        return try {
            val deleted = zikrTranslationJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr_translation")
                Log.info("✅ Soft-deleted ZikrTranslation: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrTranslation: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrTranslations(updatedAt: Instant): List<ZikrTranslationModel> =
        zikrTranslationJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}
