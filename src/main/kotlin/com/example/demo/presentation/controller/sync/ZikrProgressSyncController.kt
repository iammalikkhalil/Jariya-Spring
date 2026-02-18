package com.example.demo.presentation.controller.sync

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.dto.sync.ZikrProgressBulkSyncRequestDto
import com.example.demo.presentation.service.sync.ZikrProgressSyncService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sync")
class ZikrProgressSyncController(
    private val zikrProgressSyncService: ZikrProgressSyncService
) {

    private val log = LoggerFactory.getLogger(ZikrProgressSyncController::class.java)

    @PostMapping("/zikrprogressbulk")
    fun bulkSync(
        @Valid @RequestBody request: ZikrProgressBulkSyncRequestDto
    ): ApiResponse<ZikrPointBulkSyncResponseDto> {

        log.info(
            "[API] 📥 🚀 Bulk zikr progress sync → START | endpoint=/api/sync/zikrprogressbulk | itemsCount={}",
            request.items.size
        )

        request.items.forEachIndexed { index, item ->
            log.info(
                "📌 ZikrProgress Item[{}] → zikrId={} | userId={} | id={}",
                index,
                item.zikrId,
                item.userId,
                item.id
            )
        }

        val result = zikrProgressSyncService.bulkPersistFromClient(request.items)

        log.info("[API] 📤 ✅ Bulk zikr progress sync → SUCCESS | status=200")

        return ApiResponse.success(
            status = HttpStatus.OK,
            message = "ZikrProgress bulk sync completed successfully",
            data = result
        )
    }
}
