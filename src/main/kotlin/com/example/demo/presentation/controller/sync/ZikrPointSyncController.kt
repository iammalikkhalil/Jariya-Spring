package com.example.demo.presentation.controller.sync

import com.example.demo.infrastructure.utils.Log
import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncRequestDto
import com.example.demo.presentation.dto.sync.ZikrPointBulkSyncResponseDto
import com.example.demo.presentation.service.sync.ZikrPointSyncService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sync")
class ZikrPointSyncController(
    private val zikrPointSyncService: ZikrPointSyncService
) {

    @PostMapping("/zikrpointsbulk")
    fun bulkSync(
        @Valid @RequestBody request: ZikrPointBulkSyncRequestDto
    ): ApiResponse<ZikrPointBulkSyncResponseDto> {

        request.items.forEachIndexed { index, item ->
            Log.info("📌 Item[$index] sourceUserId=${item.sourceUserId}")
        }

        val result = zikrPointSyncService.bulkPersistFromClient(request.items)

        return ApiResponse.success(
            status = HttpStatus.OK,
            message = "ZikrPoint bulk sync completed successfully",
            data = result
        )
    }
}
