package com.example.demo.data.repository.impl.progress

import com.example.demo.data.entity.QuranProgressEntity
import com.example.demo.data.mapper.progress.toEntity
import com.example.demo.data.mapper.progress.toModel
import com.example.demo.data.repository.jpa.progress.QuranProgressJpaRepository
import com.example.demo.domain.model.progress.QuranProgressModel
import com.example.demo.domain.repository.progress.QuranProgressRepository
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.infrastructure.utils.toUUID
import com.example.demo.presentation.dto.sync.QuranProgressSyncDto
import com.example.demo.presentation.dto.sync.SyncAcknowledgeDto
import com.example.demo.presentation.dto.sync.SyncSummaryDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class QuranProgressRepositoryImpl(
    private val quranProgressJpaRepository: QuranProgressJpaRepository,
    private val syncLogRepository: SyncLogRepository
) : QuranProgressRepository {

    private val log = LoggerFactory.getLogger(QuranProgressRepositoryImpl::class.java)

    // -------------------- READ --------------------

    @Transactional(readOnly = true)
    override fun getAllQuranProgresses(): List<QuranProgressModel> {
        log.info("[REPO] 🔹 🚀 Fetch all Quran progress → START")

        val result = quranProgressJpaRepository.findAllActive()
            .map { it.toModel() }

        log.info("[REPO] ✅ 🔹 Fetch all Quran progress → SUCCESS | count={}", result.size)

        return result
    }

    @Transactional(readOnly = true)
    override fun getQuranProgressById(id: String): QuranProgressModel? {
        log.info("[REPO] 🔹 🚀 Fetch Quran progress by id → START | id={}", id)

        val result = quranProgressJpaRepository.findById(id.toUUID())
            .orElse(null)
            ?.toModel()

        return result
    }

    // -------------------- CREATE --------------------

    @Transactional
    override fun createQuranProgress(quranProgress: QuranProgressModel): Boolean {
        return try {
            quranProgressJpaRepository.save(quranProgress.toEntity())
            syncLogRepository.updateSyncLog("quran_progress")
            true
        } catch (e: Exception) {
            log.error("[REPO] 🔴 ❌ Create Quran progress → FAILED | {}", e.message, e)
            false
        }
    }

    // -------------------- UPDATE --------------------

    @Transactional
    override fun updateQuranProgress(quranProgress: QuranProgressModel): Boolean {
        return try {
            if (!quranProgressJpaRepository.existsById(quranProgress.id.toUUID())) {
                return false
            }

            quranProgressJpaRepository.save(quranProgress.toEntity())
            syncLogRepository.updateSyncLog("quran_progress")
            true
        } catch (e: Exception) {
            log.error("[REPO] 🔴 ❌ Update Quran progress → FAILED | {}", e.message, e)
            false
        }
    }

    // -------------------- DELETE --------------------

    @Transactional
    override fun deleteQuranProgress(id: String): Boolean {
        return try {
            val updated = quranProgressJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (updated > 0) {
                syncLogRepository.updateSyncLog("quran_progress")
                true
            } else false
        } catch (e: Exception) {
            log.error("[REPO] 🔴 ❌ Delete Quran progress → FAILED | {}", e.message, e)
            false
        }
    }

    // -------------------- BULK SYNC --------------------

    @Transactional(rollbackFor = [Exception::class])
    override fun bulkPersistFromClient(
        items: List<QuranProgressSyncDto>
    ): ZikrPointBulkSyncResponseDto {

        log.info("[REPO] 📥 🚀 Bulk Quran progress sync → START | itemsCount={}", items.size)

        if (items.isEmpty()) {
            return ZikrPointBulkSyncResponseDto(
                summary = SyncSummaryDto(syncedAt = Instant.now()),
                acknowledge = emptyList()
            )
        }

        val now = Instant.now()

        val existingMap = quranProgressJpaRepository
            .findAllById(items.map { it.id.toUUID() })
            .associateBy { it.id }

        val toSave = mutableListOf<QuranProgressEntity>()
        val acknowledge = mutableListOf<SyncAcknowledgeDto>()

        var created = 0
        var updated = 0
        var failed = 0

        items.forEach { dto ->
            try {
                val entity = dto.toEntity()
                toSave += entity

                if (existingMap.containsKey(entity.id)) {
                    updated++
                    acknowledge += SyncAcknowledgeDto(dto.id, "UPDATED")
                } else {
                    created++
                    acknowledge += SyncAcknowledgeDto(dto.id, "CREATED")
                }

            } catch (e: Exception) {
                failed++
                acknowledge += SyncAcknowledgeDto(dto.id, "FAILED", e.message)
                log.error("[REPO] 🔴 ❌ Quran bulk item failed | id={} | {}", dto.id, e.message, e)
            }
        }

        quranProgressJpaRepository.saveAll(toSave)
        syncLogRepository.updateSyncLog("quran_progress")

        log.info(
            "[REPO] ✅ 🔹 Bulk Quran progress sync → SUCCESS | created={} | updated={} | failed={}",
            created, updated, failed
        )

        return ZikrPointBulkSyncResponseDto(
            summary = SyncSummaryDto(
                syncedAt = now,
                created = created,
                updated = updated,
                failed = failed
            ),
            acknowledge = acknowledge
        )
    }

    // -------------------- OTHER --------------------

    @Transactional(readOnly = true)
    override fun getUncompletedRecords(): List<QuranProgressModel> {
        return quranProgressJpaRepository.findUncompleted()
            .map { it.toModel() }
    }

    @Transactional
    override fun incrementQuranProgress(id: String, level: Int): Boolean {
        return quranProgressJpaRepository
            .incrementProgress(id.toUUID(), level, Instant.now()) > 0
    }

    @Transactional
    override fun markQuranProgressAsComplete(id: String): Boolean {
        return quranProgressJpaRepository
            .markAsComplete(id.toUUID(), Instant.now()) > 0
    }
}
