package com.example.demo.presentation.controller.sync

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.sync.GoalProgressBulkSyncRequestDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.service.sync.GoalProgressSyncService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sync")
class GoalProgressSyncController(
    private val goalProgressSyncService: GoalProgressSyncService
) {

    private val log = LoggerFactory.getLogger(GoalProgressSyncController::class.java)

    @PostMapping("/goalprogressbulk")
    fun bulkSync(
        @Valid @RequestBody request: GoalProgressBulkSyncRequestDto
    ): ApiResponse<ZikrPointBulkSyncResponseDto> {

        log.info("[API] 📥 🚀 Bulk goal progress sync → START | endpoint=/api/sync/goalprogressbulk | itemsCount={}", request.items.size)

        request.items.forEachIndexed { index, item ->
            if (item.zikrId == null) {
                log.info("[API] ⚠️ 🔹 ZikrId missing → CONTINUE | index={}", index)
            }
            if (item.goalId == null) {
                log.warn("[API] ⚠️ 🔹 GoalId missing → CONTINUE | index={}", index)
            }
            if (item.userId == null) {
                log.warn("[API] ⚠️ 🔹 UserId missing → CONTINUE | index={}", index)
            }
        }

        log.info("[SERVICE] 🔹 🚀 Bulk goal progress persist → START | itemsCount={}", request.items.size)

        val result = goalProgressSyncService.bulkPersistFromClient(request.items)

        log.info("[SERVICE] ✅ 🔹 Bulk goal progress persist → SUCCESS | persistedCount={}", result.summary)

        log.info("[API] 📤 ✅ Bulk goal progress sync → SUCCESS | status=200")

        return ApiResponse.success(
            status = HttpStatus.OK,
            message = "GoalProgress bulk sync completed successfully",
            data = result
        )
    }
}