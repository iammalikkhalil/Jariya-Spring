package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.mapper.zikr.toZikrEntity
import com.example.demo.data.repository.jpa.zikr.*
import com.example.demo.domain.model.zikr.*
import com.example.demo.domain.repository.sync.SyncLogRepository
import com.example.demo.domain.repository.zikr.ZikrRepository
import com.example.demo.domain.repository.zikr.ZikrTranslationRepository
import com.example.demo.infrastructure.utils.Log
import com.example.demo.infrastructure.utils.generateUUID
import com.example.demo.infrastructure.utils.toUUID
import com.example.demo.presentation.dto.zikr.CsvZikrDto
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*
import kotlin.system.measureTimeMillis

@Repository
class ZikrRepositoryImpl(
    private val zikrJpaRepository: ZikrJpaRepository,
    private val zikrRewardRepositoryImpl: ZikrRewardRepositoryImpl,
    private val zikrQualityRepositoryImpl: ZikrQualityRepositoryImpl,
    private val zikrTranslationRepository: ZikrTranslationRepository,
    private val zikrHadithRepositoryImpl: ZikrHadithRepositoryImpl,
    private val zikrHadithTranslationRepositoryImpl: ZikrHadithTranslationRepositoryImpl,
    private val syncLogRepository: SyncLogRepository
) : ZikrRepository {

    // ✅ Optimized fetch (single query, no lazy loads)
    @Transactional(readOnly = true)
    override fun getAllZikrs(): List<ZikrModel> {
        val start = System.currentTimeMillis()
        Log.info("⏱ Fetching all Zikrs (JOIN FETCH)...")

        lateinit var result: List<ZikrModel>
        val dbTime = measureTimeMillis {
            val entities = zikrJpaRepository.findAllActive()
            result = entities.asSequence().map { it.toModel() }.toList()
        }

        Log.info("✅ getAllZikrs completed in ${System.currentTimeMillis() - start}ms (DB=${dbTime}ms)")
        return result
    }

    @Transactional(readOnly = true)
    override fun getZikrById(id: String): ZikrModel? {
        return zikrJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun createZikr(zikr: ZikrModel): Boolean {
        return try {
            zikrJpaRepository.save(zikr.toEntity())
            syncLogRepository.updateSyncLog("zikr")
            Log.info("✅ Created Zikr: ${zikr.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error creating zikr: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateZikr(zikr: ZikrModel): Boolean {
        return try {
            if (!zikrJpaRepository.existsById(zikr.id.toUUID())) return false
            zikrJpaRepository.save(zikr.toEntity())
            syncLogRepository.updateSyncLog("zikr")
            Log.info("✅ Updated Zikr: ${zikr.id}")
            true
        } catch (e: Exception) {
            Log.error("❌ Error updating zikr: ${e.message}", e)
            false
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteZikr(id: String): Boolean {
        return try {
            val deleted = zikrJpaRepository.markAsDeleted(id.toUUID(), Instant.now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr")
                Log.info("🗑 Soft-deleted Zikr: $id")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.error("❌ Error deleting zikr: ${e.message}", e)
            false
        }
    }

    @Transactional(readOnly = true)
    override fun getUpdatedZikrs(updatedAt: Instant): List<ZikrModel> {
        val start = System.currentTimeMillis()
        val result = zikrJpaRepository.findUpdatedAfter(updatedAt).map { it.toModel() }
        Log.info("✅ getUpdatedZikrs fetched ${result.size} records in ${System.currentTimeMillis() - start}ms")
        return result
    }

    // ✅ Bulk insert optimized
    @Transactional(rollbackFor = [Exception::class])
    override fun bulkInsertZikrs(rows: List<CsvZikrDto>): List<Pair<Int, UUID>> {
        val successfulEntries = mutableListOf<Pair<Int, UUID>>()
        val updatedTables = mutableSetOf<String>()
        val now = Instant.now()

        rows.forEachIndexed { index, row ->
            try {
                val zikrIdString = generateUUID()
                val zikrId = zikrIdString.toUUID()

                // Base zikr
                val zikrEntity = row.toZikrEntity(zikrId)
                zikrJpaRepository.save(zikrEntity)
                updatedTables.add("zikr")

                // Translations
                row.translationUrdu?.let {
                    zikrTranslationRepository.createZikrTranslation(
                        ZikrTranslationModel(zikrId = zikrIdString, translation = it, languageCode = "ur")
                    )
                    updatedTables.add("zikr_translation")
                }
                row.translationEnglish?.let {
                    zikrTranslationRepository.createZikrTranslation(
                        ZikrTranslationModel(zikrId = zikrIdString, translation = it, languageCode = "en")
                    )
                    updatedTables.add("zikr_translation")
                }

                // Rewards
                row.rewards?.forEach {
                    zikrRewardRepositoryImpl.createZikrReward(ZikrRewardModel(zikrId = zikrIdString, text = it))
                }
                if (!row.rewards.isNullOrEmpty()) updatedTables.add("zikr_reward")

                // Qualities
                row.qualities?.forEach {
                    zikrQualityRepositoryImpl.createZikrQuality(ZikrQualityModel(zikrId = zikrIdString, text = it))
                }
                if (!row.qualities.isNullOrEmpty()) updatedTables.add("zikr_quality")

                // Hadith + translations
                if (row.hadith != null && row.hadithReference != null) {
                    val hadithId = zikrHadithRepositoryImpl.createZikrHadith(
                        ZikrHadithModel(zikrId = zikrIdString, textAr = row.hadith, reference = row.reference ?: "")
                    )
                    updatedTables.add("zikr_hadith")

                    row.hadithTranslationUr?.let {
                        zikrHadithTranslationRepositoryImpl.createZikrHadithTranslation(
                            ZikrHadithTranslationModel(hadithId = hadithId.toString(), translation = it, languageCode = "ur")
                        )
                        updatedTables.add("zikr_hadith_translation")
                    }
                    row.hadithTranslationEn?.let {
                        zikrHadithTranslationRepositoryImpl.createZikrHadithTranslation(
                            ZikrHadithTranslationModel(hadithId = hadithId.toString(), translation = it, languageCode = "en")
                        )
                        updatedTables.add("zikr_hadith_translation")
                    }
                }

                successfulEntries.add(index to zikrId)
            } catch (e: Exception) {
                Log.error("❌ Failed to insert zikr at index $index: ${e.message}", e)
            }
        }

        // ✅ Update sync logs once per table
        updatedTables.forEach { syncLogRepository.updateSyncLog(it) }
        Log.info("✅ Sync logs updated for ${updatedTables.size} tables: $updatedTables")

        return successfulEntries
    }
}
