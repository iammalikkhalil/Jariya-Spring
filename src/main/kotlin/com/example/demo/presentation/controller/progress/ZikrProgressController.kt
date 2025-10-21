package com.example.demo.presentation.controller.progress

import com.example.demo.domain.repository.progress.ZikrProgressRepository
import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.progress.ZikrProgressRequestDto
import com.example.demo.presentation.service.progress.ZikrProgressService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/zikrProgress")
class ZikrProgressController(
    private val zikrProgressRepository: ZikrProgressRepository,
    private val zikrProgressService: ZikrProgressService,
) {

    @GetMapping("/getAll")
    fun getAllZikrProgress(): ResponseEntity<ApiResponse<Any>> {
        val all = zikrProgressRepository.getAllZikrProgresses()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrProgress records", all))
    }

    @PostMapping("/getById")
    fun getZikrProgressById(@RequestBody body: ZikrProgressRequestDto): ResponseEntity<ApiResponse<Any>> {
        val zikrProgress = zikrProgressRepository.getZikrProgressById(body.id)
        return if (zikrProgress != null)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrProgress found", zikrProgress))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrProgress not found"))
    }

    @PostMapping("/add")
    fun createZikrProgress(@RequestBody body: ZikrProgressRequestDto): ResponseEntity<ApiResponse<Any>> {
        val created = zikrProgressService.createZikrProgress(body)
        return if (created)
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrProgress created successfully", null))
        else
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrProgress"))
    }

    @PostMapping("/update")
    fun updateZikrProgress(@RequestBody body: ZikrProgressRequestDto): ResponseEntity<ApiResponse<Any>> {
        val updated = zikrProgressService.updateZikrProgress(body)
        return if (updated)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrProgress updated successfully", null))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrProgress not found or update failed"))
    }

    @PostMapping("/deleteById")
    fun deleteZikrProgress(@RequestBody body: ZikrProgressRequestDto): ResponseEntity<ApiResponse<Any>> {
        val deleted = zikrProgressRepository.deleteZikrProgress(body.id)
        return if (deleted)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrProgress deleted successfully", null))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrProgress not found or delete failed"))
    }

    @GetMapping("/getUncompletedRecord")
    fun getUncompletedRecord(): ResponseEntity<ApiResponse<Any>> {
        zikrProgressService.processUncompletedAsync()
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK, "ZikrProgress processing started in background", null)
        )
    }
}
