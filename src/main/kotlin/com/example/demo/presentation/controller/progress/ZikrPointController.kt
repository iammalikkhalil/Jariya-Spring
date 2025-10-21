package com.example.demo.presentation.controller.progress

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.progress.ZikrPointRequestDto
import com.example.demo.presentation.dto.progress.ZikrPointSummaryRequestDto
import com.example.demo.presentation.service.progress.ZikrPointService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/zikrPoint")
class ZikrPointController(
    private val zikrPointService: ZikrPointService
) {

    @GetMapping("/getAll")
    fun getAllZikrPoints(): ResponseEntity<ApiResponse<Any>> {
        val zikrPoints = zikrPointService.getAllZikrPoints()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrPoint records", zikrPoints))
    }

    @PostMapping("/getById")
    fun getZikrPointById(@RequestBody body: ZikrPointRequestDto): ResponseEntity<ApiResponse<Any>> {
        val zikrPoint = zikrPointService.getZikrPointById(body.id)
        return if (zikrPoint != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrPoint found", zikrPoint))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrPoint not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrPoint(@RequestBody body: ZikrPointRequestDto): ResponseEntity<ApiResponse<Any>> {
        val created = zikrPointService.createZikrPoint(body)
        return if (created)
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrPoint created successfully", null))
        else
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrPoint"))
    }

    @PostMapping("/update")
    fun updateZikrPoint(@RequestBody body: ZikrPointRequestDto): ResponseEntity<ApiResponse<Any>> {
        val updated = zikrPointService.updateZikrPoint(body)
        return if (updated)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrPoint updated successfully", null))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrPoint not found or update failed"))
    }

    @PostMapping("/deleteById")
    fun deleteZikrPoint(@RequestBody body: ZikrPointRequestDto): ResponseEntity<ApiResponse<Any>> {
        val deleted = zikrPointService.deleteZikrPoint(body.id)
        return if (deleted)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrPoint deleted successfully", null))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrPoint not found or delete failed"))
    }

    @PostMapping("/getZikrPointSummary")
    fun getZikrPointSummary(@RequestBody body: ZikrPointSummaryRequestDto): ResponseEntity<ApiResponse<Any>> {
        val summary = zikrPointService.getZikrPointSummary(body.id)
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "User summary fetched successfully", summary))
    }

    @GetMapping("/getZikrPointTotal")
    fun getZikrPointTotal(): ResponseEntity<ApiResponse<Any>> {
        val total = zikrPointService.getZikrPointTotal()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "User total fetched successfully", total))
    }

    @GetMapping("/getLeaderboard")
    fun getLeaderboard(): ResponseEntity<ApiResponse<Any>> {
        val leaderboard = zikrPointService.getLeaderboard()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Leaderboard fetched successfully", leaderboard))
    }

    @GetMapping("/getZikrLeaderboard")
    fun getZikrLeaderboard(): ResponseEntity<ApiResponse<Any>> {
        val leaderboard = zikrPointService.getZikrLeaderboard()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr leaderboard fetched successfully", leaderboard))
    }
}
