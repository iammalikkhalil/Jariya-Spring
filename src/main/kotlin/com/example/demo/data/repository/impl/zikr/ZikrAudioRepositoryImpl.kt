package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.repository.jpa.zikr.ZikrAudioJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.model.zikr.ZikrAudioModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrAudioRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class ZikrAudioRepositoryImpl(
    private val zikrAudioJpaRepository: ZikrAudioJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrAudioRepository {

    override fun getAllZikrAudios(): List<ZikrAudioModel> =
        zikrAudioJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrAudioById(id: String): ZikrAudioModel? =
        zikrAudioJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createZikrAudio(zikrAudio: ZikrAudioModel): Boolean {
        return try {
            val zikrEntity = zikrJpaRepository.findById(zikrAudio.zikrId.toUUID()).orElse(null) ?: return false
            zikrAudioJpaRepository.save(zikrAudio.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_audio")
            Log.info("✅ Created ZikrAudio: ${zikrAudio.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating ZikrAudio: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun updateZikrAudio(zikrAudio: ZikrAudioModel): Boolean {
        return try {
            if (!zikrAudioJpaRepository.existsById(zikrAudio.id.toUUID())) return false
            val zikrEntity = zikrJpaRepository.findById(zikrAudio.zikrId.toUUID()).orElse(null) ?: return false
            zikrAudioJpaRepository.save(zikrAudio.toEntity(zikrEntity))
            syncLogRepository.updateSyncLog("zikr_audio")
            Log.info("✅ Updated ZikrAudio: ${zikrAudio.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrAudio: ${e.message}", e)
            false
        }
    }

    @Transactional
    override fun deleteZikrAudio(id: String): Boolean {
        return try {
            val updated = zikrAudioJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (updated > 0) {
                syncLogRepository.updateSyncLog("zikr_audio")
                Log.info("✅ Deleted ZikrAudio (soft): $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrAudio: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrAudios(updatedAt: Instant): List<ZikrAudioModel> =
        zikrAudioJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}