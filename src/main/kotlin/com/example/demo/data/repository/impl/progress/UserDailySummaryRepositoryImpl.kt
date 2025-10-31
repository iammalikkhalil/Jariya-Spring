package com.example.demo.data.repository.impl.progress

import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.auth.UserJpaRepository
import com.example.demo.data.repository.jpa.progress.UserDailySummaryJpaRepository
import com.example.demo.domain.model.progress.UserDailySummaryModel
import com.example.demo.domain.repository.progress.UserDailySummaryRepository
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class UserDailySummaryRepositoryImpl(
    private val userDailySummaryJpaRepository: UserDailySummaryJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : UserDailySummaryRepository {

    // ✅ Eager load via JOIN FETCH, minimal queries
    @Transactional(readOnly = true)
    override fun getAllUserDailySummaries(): List<UserDailySummaryModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching user_daily_summary records (eager)...")

        val result = userDailySummaryJpaRepository.findAllActive().map { it.toModel() }

        Log.info("✅ getAllUserDailySummaries completed in ${System.currentTimeMillis() - start}ms (${result.size} records)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getUserDailySummaryById(id: String): UserDailySummaryModel? =
        userDailySummaryJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional(readOnly = true)
    override fun getUserDailySummaryByUserId(id: String): UserDailySummaryModel? =
        userDailySummaryJpaRepository.findActiveByUserId(id.toUUID())?.toModel()

    @Transactional(rollbackFor = [Exception::class])
    override fun createUserDailySummary(userDailySummary: UserDailySummaryModel): Boolean {
        return try {
            val userEntity = userJpaRepository.getReferenceById(userDailySummary.userId.toUUID())
            userDailySummaryJpaRepository.save(userDailySummary.toEntity(userEntity))
            syncLogRepository.updateSyncLog("user_daily_summary")
            Log.info("✅ Created UserDailySummary: ${userDailySummary.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating UserDailySummary: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun updateUserDailySummary(userDailySummary: UserDailySummaryModel): Boolean {
        return try {
            if (!userDailySummaryJpaRepository.existsById(userDailySummary.id.toUUID())) return false
            val userEntity = userJpaRepository.getReferenceById(userDailySummary.userId.toUUID())
            userDailySummaryJpaRepository.save(userDailySummary.toEntity(userEntity))
            syncLogRepository.updateSyncLog("user_daily_summary")
            Log.info("✅ Updated UserDailySummary: ${userDailySummary.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating UserDailySummary: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun deleteUserDailySummary(id: String): Boolean {
        return try {
            val deleted = userDailySummaryJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("user_daily_summary")
                Log.info("🗑 Soft deleted UserDailySummary: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting UserDailySummary: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    fun getUpdatedUserDailySummaries(updatedAt: Instant): List<UserDailySummaryModel> =
        userDailySummaryJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
}
