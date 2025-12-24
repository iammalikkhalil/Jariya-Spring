package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toDomain
import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.repository.jpa.zikr.ZikrGoalJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrGoalMapJpaRepository
import com.example.demo.data.repository.jpa.zikr.ZikrJpaRepository
import com.example.demo.domain.exception.AppException
import com.example.demo.domain.model.zikr.ZikrGoalMapModel
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrGoalMapRepository
import com.example.demo.infrastructure.constants.MessageConstants
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.toUUID
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.system.measureTimeMillis

@Repository
class ZikrGoalMapRepositoryImpl(
    private val zikrGoalMapJpaRepository: ZikrGoalMapJpaRepository,
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrGoalJpaRepository: ZikrGoalJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : ZikrGoalMapRepository
{

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getAllGoalMaps(): List<ZikrGoalMapModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ [getAllGoalMaps] Fetching ZikrGoalMap records...")

        val entities = zikrGoalMapJpaRepository.findAllActive()
        val result = entities.map { it.toDomain() }

        Log.info("✅ getAllGoalMaps completed in ${System.currentTimeMillis() - start} ms (${result.size})")
        return result
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getGoalMapById(id: String): ZikrGoalMapModel? {
        return zikrGoalMapJpaRepository
            .findById(id.toUUID())
            .orElse(null)
            ?.takeIf { it.deletedAt == null }
            ?.toDomain()
    }


    @Transactional(rollbackFor = [Exception::class])
    override fun createGoalMap(goalMap: ZikrGoalMapModel): Boolean {
        val startTime = System.currentTimeMillis()
        Log.info("⏱️ Starting createGoalMap for goalMap ID: ${goalMap.id} at ${System.currentTimeMillis()}")

        return try {
            // 🔒 Validate UUID format
            val uuidValidationStart = System.currentTimeMillis()
            try {
                goalMap.zikrId.toUUID()
                goalMap.goalId.toUUID()
            } catch (e: IllegalArgumentException) {
                Log.warn("Invalid UUID format for zikrId: ${goalMap.zikrId} or goalId: ${goalMap.goalId}")
                throw AppException(
                    status = HttpStatus.BAD_REQUEST,
                    message = MessageConstants.General.INVALID_INPUT,
                )
            }
            val uuidValidationTime = System.currentTimeMillis() - uuidValidationStart
            if (uuidValidationTime > 10) {
                Log.warn("⚠️ UUID validation took ${uuidValidationTime}ms")
            }

            // 🔒 FK: Zikr must exist & not deleted
            Log.info("🔍 Validating Zikr with ID: ${goalMap.zikrId}")
            val zikrFetchStart = System.currentTimeMillis()
            val zikr = zikrJpaRepository.findById(goalMap.zikrId.toUUID())
                .orElseThrow {
                    Log.warn("Zikr not found with ID: ${goalMap.zikrId}")
                    AppException(
                        status = HttpStatus.NOT_FOUND,
                        message = MessageConstants.Zikr.NOT_FOUND,
                    )
                }
            val zikrFetchTime = System.currentTimeMillis() - zikrFetchStart
            Log.info("✅ Zikr fetch completed in ${zikrFetchTime}ms")

            if (zikr.deletedAt != null) {
                Log.warn("Zikr is soft-deleted: ID=${zikr.id}")
                throw AppException(
                    status = HttpStatus.BAD_REQUEST,
                    message = MessageConstants.Zikr.ALREADY_DELETED,
                )
            }

            // 🔒 FK: Goal must exist & not deleted
            Log.info("🔍 Validating Goal with ID: ${goalMap.goalId}")
            val goalFetchStart = System.currentTimeMillis()
            val goal = zikrGoalJpaRepository.findById(goalMap.goalId.toUUID())
                .orElseThrow {
                    Log.warn("Goal not found with ID: ${goalMap.goalId}")
                    AppException(
                        status = HttpStatus.NOT_FOUND,
                        message = MessageConstants.Goal.NOT_FOUND,
                    )
                }
            val goalFetchTime = System.currentTimeMillis() - goalFetchStart
            Log.info("✅ Goal fetch completed in ${goalFetchTime}ms")

            if (goal.deletedAt != null) {
                Log.warn("Goal is soft-deleted: ID=${goal.id}")
                throw AppException(
                    status = HttpStatus.BAD_REQUEST,
                    message = MessageConstants.Goal.ALREADY_DELETED,
                )
            }

            try {
                // Check for existing mapping (optional - uncomment if needed)
                /*
                val duplicateCheckStart = System.currentTimeMillis()
                val existingMapping = zikrGoalMapJpaRepository.findByZikrAndGoal(zikr, goal)
                if (existingMapping != null) {
                    Log.warn("ZikrGoalMap already exists for zikrId=${zikr.id}, goalId=${goal.id}")
                    throw AppException(
                        status = HttpStatus.CONFLICT,
                        message = MessageConstants.GoalMap.ALREADY_EXISTS,
                    )
                }
                val duplicateCheckTime = System.currentTimeMillis() - duplicateCheckStart
                Log.debug("Duplicate check took ${duplicateCheckTime}ms")
                */

                Log.info("🏗️ Creating ZikrGoalMap entity")
                val entityCreationStart = System.currentTimeMillis()
                val goalMapEntity = goalMap.toEntity(zikr, goal)
                val entityCreationTime = System.currentTimeMillis() - entityCreationStart
                if (entityCreationTime > 50) {
                    Log.warn("⚠️ Entity creation took ${entityCreationTime}ms")
                }

                Log.info("💾 Saving ZikrGoalMap entity")
                val saveStart = System.currentTimeMillis()
                zikrGoalMapJpaRepository.save(goalMapEntity)
                val saveTime = System.currentTimeMillis() - saveStart
                Log.info("✅ Entity saved in ${saveTime}ms with ID: ${goalMap.id}")

                // Update sync log
                Log.info("📝 Updating sync log for 'zikr_goal_map'")
                val syncLogStart = System.currentTimeMillis()
                syncLogRepository.updateSyncLog("zikr_goal_map")
                val syncLogTime = System.currentTimeMillis() - syncLogStart
                Log.info("✅ Sync log updated in ${syncLogTime}ms")

                val totalTime = System.currentTimeMillis() - startTime
                Log.info("🎉 Successfully created ZikrGoalMap: ${goalMap.id} in ${totalTime}ms")

                // Performance summary
                Log.info("📊 Performance Summary:")
                Log.info("   - Total time: ${totalTime}ms")
                Log.info("   - Zikr fetch: ${zikrFetchTime}ms")
                Log.info("   - Goal fetch: ${goalFetchTime}ms")
                Log.info("   - Entity creation: ${entityCreationTime}ms")
                Log.info("   - Save operation: ${saveTime}ms")
                Log.info("   - Sync log: ${syncLogTime}ms")

                return true

            } catch (e: DataIntegrityViolationException) {
                val errorTime = System.currentTimeMillis() - startTime
                Log.error("❌ Data integrity violation after ${errorTime}ms while creating ZikrGoalMap", e)
                throw e
            } catch (e: AppException) {
                val errorTime = System.currentTimeMillis() - startTime
                Log.warn("⚠️ AppException after ${errorTime}ms: ${e.message}")
                throw e
            } catch (e: Exception) {
                val errorTime = System.currentTimeMillis() - startTime
                Log.error("💥 Unexpected error after ${errorTime}ms creating ZikrGoalMap", e)
                throw AppException(
                    status = HttpStatus.INTERNAL_SERVER_ERROR,
                    message = MessageConstants.General.OPERATION_FAILED,
                )
            }
        } finally {
            val finalTime = System.currentTimeMillis() - startTime
            Log.info("🏁 Total execution time for createGoalMap: ${finalTime}ms")

            // Performance warning for slow operations
            when {
                finalTime > 1000 -> Log.error("🚨 CRITICAL: createGoalMap took ${finalTime}ms (>1s)")
                finalTime > 500 -> Log.warn("⚠️ WARNING: createGoalMap took ${finalTime}ms (>500ms)")
                finalTime > 100 -> Log.info("ℹ️  INFO: createGoalMap took ${finalTime}ms (>100ms)")
            }
        }
    }



    @Transactional(rollbackFor = [Exception::class])
    override fun updateGoalMap(goalMap: ZikrGoalMapModel): Boolean {
        return try {
            val existing = zikrGoalMapJpaRepository
                .findById(goalMap.id.toUUID())
                .orElse(null)
                ?: return false

            if (existing.deletedAt != null) return false

            // 🔒 FK: Zikr check
            val zikr = zikrJpaRepository.findById(goalMap.zikrId.toUUID())
                .orElse(null)
                ?: return false
            if (zikr.deletedAt != null) return false

            // 🔒 FK: Goal check
            val goal = zikrGoalJpaRepository.findById(goalMap.goalId.toUUID())
                .orElse(null)
                ?: return false
            if (goal.deletedAt != null) return false

            zikrGoalMapJpaRepository.save(
                goalMap.toEntity(zikr, goal)
            )

            syncLogRepository.updateSyncLog("zikr_goal_map")
            Log.info("✅ Updated ZikrGoalMap: ${goalMap.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating ZikrGoalMap: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteGoalMap(id: String): Boolean {
        return try {
            val affected = zikrGoalMapJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (affected > 0) {
                syncLogRepository.updateSyncLog("zikr_goal_map")
                Log.info("🗑 Soft-deleted ZikrGoalMap: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting ZikrGoalMap: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun getUpdatedGoalMaps(updatedAt: Instant): List<ZikrGoalMapModel> {
        return zikrGoalMapJpaRepository
            .findUpdatedAfter(updatedAt)
            .map { it.toDomain() }
    }
}

