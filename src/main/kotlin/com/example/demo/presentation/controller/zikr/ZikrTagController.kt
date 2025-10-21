package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrTagDtoRequest
import com.example.demo.presentation.service.zikr.ZikrTagService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/zikrTag")
class ZikrTagController(
    private val zikrTagService: ZikrTagService
) {

    @GetMapping("/getAll")
    fun getAllZikrTags(): ResponseEntity<ApiResponse<Any>> {
        val zikrTags = zikrTagService.getAllZikrTags()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrTag records", zikrTags))
    }

    @PostMapping("/getById")
    fun getZikrTagById(@RequestBody body: ZikrTagDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val zikrTag = zikrTagService.getZikrTagById(body.id)
        return if (zikrTag != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrTag found", zikrTag))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTag not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrTag(@RequestBody body: ZikrTagDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val result = zikrTagService.createZikrTag(body)
        return if (result) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrTag created successfully", body))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrTag"))
        }
    }

    @PostMapping("/update")
    fun updateZikrTag(@RequestBody body: ZikrTagDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrTagService.updateZikrTag(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrTag updated successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTag not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrTag(@RequestBody body: ZikrTagDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrTagService.deleteZikrTag(body.id)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrTag deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTag not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrTagService.getUpdatedZikrTags(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
