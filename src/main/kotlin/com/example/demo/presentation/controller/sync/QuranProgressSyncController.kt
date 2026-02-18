package com.example.demo.presentation.controller.sync

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.sync.QuranProgressBulkSyncRequestDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.service.sync.QuranProgressSyncService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sync")
class QuranProgressSyncController(
    private val quranProgressSyncService: QuranProgressSyncService
) {

    private val log = LoggerFactory.getLogger(QuranProgressSyncController::class.java)

    @PostMapping("/quranprogressbulk")
    fun bulkSync(
        @Valid @RequestBody request: QuranProgressBulkSyncRequestDto
    ): ApiResponse<ZikrPointBulkSyncResponseDto> {

        log.info(
            "[API] 📥 🚀 Bulk Quran progress sync → START | endpoint=/api/sync/quranprogressbulk | itemsCount={}",
            request.items.size
        )

        request.items.forEachIndexed { index, item ->
            if (item.userId.isNullOrBlank()) {
                log.warn("[API] ⚠️ 🔹 UserId missing → CONTINUE | index={}", index)
            }
            if (item.ayahId.isBlank()) {
                log.warn("[API] ⚠️ 🔹 AyahId missing → CONTINUE | index={}", index)
            }
        }

        log.info(
            "[SERVICE] 🔹 🚀 Bulk Quran progress persist → START | itemsCount={}",
            request.items.size
        )

        val result = quranProgressSyncService.bulkPersistFromClient(request.items)

        log.info("[API] 📤 ✅ Bulk Quran progress sync → SUCCESS | status=200")

        return ApiResponse.success(
            status = HttpStatus.OK,
            message = "QuranProgress bulk sync completed successfully",
            data = result
        )
    }
}