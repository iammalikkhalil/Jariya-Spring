package com.example.demo.presentation.service.sync

import com.example.demo.domain.repository.progress.ZikrProgressRepository
import com.example.demo.presentation.dto.sync.SyncSummaryDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.dto.sync.ZikrProgressSyncDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ZikrProgressSyncService(
    private val zikrProgressRepository: ZikrProgressRepository
) {

    private val log = LoggerFactory.getLogger(ZikrProgressSyncService::class.java)

    fun bulkPersistFromClient(
        items: List<ZikrProgressSyncDto>
    ): ZikrPointBulkSyncResponseDto {

        log.info(
            "[SERVICE] 📥 🚀 Bulk zikr progress persist → START | itemsCount={}",
            items.size
        )

        if (items.isEmpty()) {
            log.warn(
                "[SERVICE] ⚠️ ⏭️ Bulk zikr progress persist → SKIPPED | reason=EmptyItemList"
            )

            return ZikrPointBulkSyncResponseDto(
                summary = SyncSummaryDto(),
                acknowledge = emptyList()
            )
        }

        val result = zikrProgressRepository.bulkPersistFromClient(items)

        log.info(
            "[SERVICE] ✅ 🔹 Bulk zikr progress persist → SUCCESS | persistedCount={}",
            result.acknowledge.size
        )

        return result
    }
}
