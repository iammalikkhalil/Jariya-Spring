package com.example.demo.presentation.service.sync

import com.example.demo.domain.repository.progress.QuranProgressRepository
import com.example.demo.presentation.dto.sync.QuranProgressSyncDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class QuranProgressSyncService(
    private val quranProgressRepository: QuranProgressRepository
) {

    private val log = LoggerFactory.getLogger(QuranProgressSyncService::class.java)

    fun bulkPersistFromClient(
        items: List<QuranProgressSyncDto>
    ): ZikrPointBulkSyncResponseDto {

        log.info(
            "[SERVICE] 📥 🚀 Bulk Quran progress persist → START | itemsCount={}",
            items.size
        )

        if (items.isEmpty()) {
            log.warn(
                "[SERVICE] ⚠️ ⏭️ Bulk Quran progress persist → SKIPPED | reason=EmptyItemList"
            )

            return ZikrPointBulkSyncResponseDto(
                summary = com.example.demo.presentation.dto.sync.SyncSummaryDto(),
                acknowledge = emptyList()
            )
        }

        val result = quranProgressRepository.bulkPersistFromClient(items)

        log.info(
            "[SERVICE] ✅ 🔹 Bulk Quran progress persist → SUCCESS | persistedCount={}",
            result.acknowledge.size
        )

        return result
    }
}
