package com.example.demo.presentation.service.sync

import com.example.demo.domain.repository.progress.GoalProgressRepository
import com.example.demo.presentation.dto.sync.GoalProgressSyncDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GoalProgressSyncService(
    private val goalProgressRepository: GoalProgressRepository
) {

    private val log = LoggerFactory.getLogger(GoalProgressSyncService::class.java)

    fun bulkPersistFromClient(
        items: List<GoalProgressSyncDto>
    ): ZikrPointBulkSyncResponseDto {

        log.info(
            "[SERVICE] 📥 🚀 Bulk goal progress persist → START | itemsCount={}",
            items.size
        )

        if (items.isEmpty()) {
            log.warn(
                "[SERVICE] ⚠️ ⏭️ Bulk goal progress persist → SKIPPED | reason=EmptyItemList"
            )

            return ZikrPointBulkSyncResponseDto(
                summary = com.example.demo.presentation.dto.sync.SyncSummaryDto(),
                acknowledge = emptyList()
            )
        }

        val result = goalProgressRepository.bulkPersistFromClient(items)

        log.info(
            "[SERVICE] ✅ 🔹 Bulk goal progress persist → SUCCESS | persistedCount={}",
            result.acknowledge.size
        )

        return result
    }
}