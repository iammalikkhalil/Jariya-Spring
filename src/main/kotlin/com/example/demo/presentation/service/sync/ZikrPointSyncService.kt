package com.example.demo.presentation.service.sync

import com.example.demo.domain.repository.progress.ZikrPointRepository
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.dto.sync.ZikrPointSyncDto
import com.example.demo.infrastructure.utils.Log
import com.example.demo.presentation.dto.sync.SyncSummaryDto
import org.springframework.stereotype.Service

@Service
class ZikrPointSyncService(
    private val zikrPointRepository: ZikrPointRepository
) {

    fun bulkPersistFromClient(
        items: List<ZikrPointSyncDto>
    ): ZikrPointBulkSyncResponseDto {

        Log.info("📥 Received ZikrPoint bulk sync (${items.size} items)")

        if (items.isEmpty()) {
            return ZikrPointBulkSyncResponseDto(
                summary = SyncSummaryDto(),
                acknowledge = emptyList()
            )
        }

        return zikrPointRepository.bulkPersistFromClient(items)
    }
}
