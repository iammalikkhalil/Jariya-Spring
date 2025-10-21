package com.example.demo.data.repository.impl.quran

import com.example.demo.data.mapper.quran.toEntity
import com.example.demo.data.mapper.quran.toModel
import com.example.demo.data.repository.jpa.quran.FifteenLinesMadaniQuranJpaRepository
import com.example.demo.domain.model.quran.FifteenLinesMadaniQuranModel
import com.example.demo.domain.repository.quran.FifteenLinesMadaniQuranRepository
import com.example.demo.infrastructure.utils.Log
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class FifteenLinesMadaniQuranRepositoryImpl(
    private val quranJpaRepository: FifteenLinesMadaniQuranJpaRepository
) : FifteenLinesMadaniQuranRepository {

    override fun getLine(pageNumber: Int, lineNumber: Int): FifteenLinesMadaniQuranModel? =
        quranJpaRepository.findByPageNumberAndLineNumber(pageNumber, lineNumber)?.toModel()

    override fun getPage(pageNumber: Int): List<FifteenLinesMadaniQuranModel> =
        quranJpaRepository.findAllByPageNumberOrderByLineNumberAsc(pageNumber).map { it.toModel() }

    @Transactional
    override fun insertLine(pageNumber: Int, lineNumber: Int, textAr: String): FifteenLinesMadaniQuranModel? {
        return try {
            val entity = FifteenLinesMadaniQuranModel(
                pageNumber = pageNumber,
                lineNumber = lineNumber,
                textAr = textAr,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            ).toEntity()
            quranJpaRepository.save(entity).toModel().also {
                Log.info("✅ Inserted Quran line (page=$pageNumber, line=$lineNumber)")
            }
        } catch (e: Exception) {
            Log.error("❌ Error inserting Quran line: ${e.message}", e)
            null
        }
    }

    @Transactional
    override fun updateLine(pageNumber: Int, lineNumber: Int, newTextAr: String): FifteenLinesMadaniQuranModel? {
        return try {
            val updated = quranJpaRepository.updateLine(pageNumber, lineNumber, newTextAr, Instant.now())
            if (updated > 0) {
                quranJpaRepository.findByPageNumberAndLineNumber(pageNumber, lineNumber)?.toModel()
            } else null
        } catch (e: Exception) {
            Log.error("❌ Error updating Quran line: ${e.message}", e)
            null
        }
    }

    @Transactional
    override fun deleteLine(pageNumber: Int, lineNumber: Int): Boolean {
        return try {
            quranJpaRepository.deleteLine(pageNumber, lineNumber) > 0
        } catch (e: Exception) {
            Log.error("❌ Error deleting Quran line: ${e.message}", e)
            false
        }
    }

    override fun getLastLine(): FifteenLinesMadaniQuranModel? =
        quranJpaRepository.findTopByOrderByPageNumberDescLineNumberDesc()?.toModel()

    @Transactional
    override fun bulkInsertLines(lines: List<FifteenLinesMadaniQuranModel>): List<FifteenLinesMadaniQuranModel> {
        return try {
            val entities = lines.map { it.copy(createdAt = Instant.now(), updatedAt = Instant.now()).toEntity() }
            quranJpaRepository.saveAll(entities).map { it.toModel() }.also {
                Log.info("✅ Bulk inserted ${it.size} Quran lines")
            }
        } catch (e: Exception) {
            Log.error("❌ Error bulk inserting Quran lines: ${e.message}", e)
            emptyList()
        }
    }
}