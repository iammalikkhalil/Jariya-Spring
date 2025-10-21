package com.example.demo.data.repository.impl.progress

import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.progress.UserTotalPointsJpaRepository
import com.example.demo.domain.model.progress.UserTotalPointsModel
import com.example.demo.domain.repository.progress.UserTotalPointsRepository
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class UserTotalPointsRepositoryImpl(
    private val userTotalPointsJpaRepository: UserTotalPointsJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : UserTotalPointsRepository {

    override fun getAllUserTotalPoints(): List<UserTotalPointsModel> =
        userTotalPointsJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getUserTotalPointById(id: String): UserTotalPointsModel? =
        userTotalPointsJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
    override fun createUserTotalPoint(userTotalPoint: UserTotalPointsModel): Boolean {
        return try {
            userTotalPointsJpaRepository.save(userTotalPoint.toEntity())
            syncLogRepository.updateSyncLog("user_total_points")
            Log.info("✅ Created UserTotalPoints: ${userTotalPoint.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating UserTotalPoints: ${e.message}", e)
            false
        }
    }

    @Transactional
    fun updateUserTotalPoint(userTotalPoint: UserTotalPointsModel): Boolean {
        return try {
            if (!userTotalPointsJpaRepository.existsById(userTotalPoint.id.toUUID())) return false
            userTotalPointsJpaRepository.save(userTotalPoint.toEntity())
            syncLogRepository.updateSyncLog("user_total_points")
            Log.info("✅ Updated UserTotalPoints: ${userTotalPoint.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating UserTotalPoints: ${e.message}", e)
            false
        }
    }

    @Transactional
    fun deleteUserTotalPoint(id: String): Boolean {
        return try {
            val deleted = userTotalPointsJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("user_total_points")
                Log.info("✅ Soft-deleted UserTotalPoints: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting UserTotalPoints: ${e.message}", e)
            false
        }
    }

    fun getUpdatedUserTotalPoints(updatedAt: Instant): List<UserTotalPointsModel> =
        userTotalPointsJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }
}
