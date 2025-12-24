package com.example.demo.presentation.controller.sync

import com.example.demo.infrastructure.utils.Log
import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.service.sync.SyncLogService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/sync")
class SyncLogController(
    private val syncLogService: SyncLogService
) {

    @GetMapping("/getAll")
    fun getAllSyncLogs(): ResponseEntity<ApiResponse<Any>> {
        val logs = syncLogService.getAllSyncLogs()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all records", logs))
    }

    @PostMapping("/getUpdated")
    fun getUpdatedSyncLogs(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {

            val updatedAt = Instant.parse(body.updatedAt)
            val logs = syncLogService.getUpdatedSyncLogs(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched updated records", logs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(
                    ApiResponse.error(
                        HttpStatus.BAD_REQUEST,
                        "Invalid datetime format. Use ISO-8601 format like 2025-09-30T12:34:56Z"
                    )
                )
        }
    }

    @PostMapping("/markUpdated")
    fun markSyncLogUpdated(): ResponseEntity<ApiResponse<Any>> {
        val updated = syncLogService.markSyncLogUpdated()
        return if (updated)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Updated successfully", null))
        else
            ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                .body(ApiResponse.error(HttpStatus.NOT_MODIFIED, "Update failed!"))
    }
}
