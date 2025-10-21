package com.example.demo.data.repository.impl.zikr

import com.example.demo.data.mapper.zikr.toEntity
import com.example.demo.data.mapper.zikr.toModel
import com.example.demo.data.mapper.zikr.toZikrEntity
import com.example.demo.data.repository.jpa.zikr.*
import com.example.demo.domain.model.zikr.ZikrHadithModel
import com.example.demo.domain.model.zikr.ZikrHadithTranslationModel
import com.example.demo.domain.model.zikr.ZikrModel
import com.example.demo.domain.model.zikr.ZikrQualityModel
import com.example.demo.domain.model.zikr.ZikrRewardModel
import com.example.demo.domain.model.zikr.ZikrTranslationModel
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
import java.time.Instant.now
import java.util.UUID

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

    override fun getAllZikrs(): List<ZikrModel> =
        zikrJpaRepository.findAllByIsDeletedFalse().map { it.toModel() }

    override fun getZikrById(id: String): ZikrModel? =
        zikrJpaRepository.findById(id.toUUID()).orElse(null)?.toModel()

    @Transactional
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

    @Transactional
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

    @Transactional
    override fun deleteZikr(id: String): Boolean {
        return try {
            val deleted = zikrJpaRepository.markAsDeleted(id.toUUID(), now())
            if (deleted > 0) {
                syncLogRepository.updateSyncLog("zikr")
                Log.info("✅ Soft-deleted Zikr: $id")
                true
            } else false
        } catch (e: Exception) {
            Log.error("❌ Error deleting zikr: ${e.message}", e)
            false
        }
    }

    override fun getUpdatedZikrs(updatedAt: Instant): List<ZikrModel> =
        zikrJpaRepository.findByUpdatedAtAfter(updatedAt).map { it.toModel() }

    // ---- BULK INSERT ----
    @Transactional
    override fun bulkInsertZikrs(rows: List<CsvZikrDto>): List<Pair<Int, UUID>> {
        val successfulEntries = mutableListOf<Pair<Int, UUID>>()
        val updatedTables = mutableSetOf<String>()
        val now = now()

        rows.forEachIndexed { index, row ->
            try {

                val zikrIdString = generateUUID()
                val zikrId = zikrIdString.toUUID()
                val zikrEntity = row.toZikrEntity(zikrId)
                zikrJpaRepository.save(zikrEntity)
                updatedTables.add("zikr")

                // Translations
                row.translationUrdu?.let {

                    zikrTranslationRepository.createZikrTranslation(ZikrTranslationModel(
                        zikrId = zikrIdString,
                        translation = it,
                        languageCode = "ur",
                    ))
                    updatedTables.add("zikr_translation")
                }
                row.translationEnglish?.let {

                    zikrTranslationRepository.createZikrTranslation(ZikrTranslationModel(
                        zikrId = zikrIdString,
                        translation = it,
                        languageCode = "en",
                    ))

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

                // Hadiths + Translations
                if (row.hadith != null && row.hadithReference != null) {
                    val hadithId = zikrHadithRepositoryImpl.createZikrHadith(ZikrHadithModel(zikrId = zikrIdString, textAr = row.hadith, reference = row.reference?: "") )
                    updatedTables.add("zikr_hadith")

                    row.hadithTranslationUr?.let {
                        zikrHadithTranslationRepositoryImpl.createZikrHadithTranslation(ZikrHadithTranslationModel(hadithId = hadithId.toString(), translation = it, languageCode = "ur"))
                        updatedTables.add("zikr_hadith_translation")
                    }
                    row.hadithTranslationEn?.let {
                        zikrHadithTranslationRepositoryImpl.createZikrHadithTranslation(ZikrHadithTranslationModel(hadithId = hadithId.toString(), translation = it, languageCode = "en"))
                        updatedTables.add("zikr_hadith_translation")
                    }
                }

                successfulEntries.add(index to zikrId)
            } catch (e: Exception) {
                Log.error("❌ Failed to insert zikr at index $index: ${e.message}", e)
            }
        }

        // update sync logs once per table
        updatedTables.forEach { syncLogRepository.updateSyncLog(it) }
        Log.info("✅ Sync logs updated for ${updatedTables.size} tables: $updatedTables")

        return successfulEntries
    }
}
