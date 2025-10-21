package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrQualityDtoRequest
import com.example.demo.presentation.service.zikr.ZikrQualityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/zikrQuality")
class ZikrQualityController(
    private val zikrQualityService: ZikrQualityService
) {

    @GetMapping("/getAll")
    fun getAllZikrQualities(): ResponseEntity<ApiResponse<Any>> {
        val zikrQualities = zikrQualityService.getAllZikrQualities()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrQuality records", zikrQualities))
    }

    @PostMapping("/getById")
    fun getZikrQualityById(@RequestBody body: ZikrQualityDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val zikrQuality = zikrQualityService.getZikrQualityById(body.id)
        return if (zikrQuality != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrQuality found", zikrQuality))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrQuality not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrQuality(@RequestBody body: ZikrQualityDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val result = zikrQualityService.createZikrQuality(body)
        return if (result) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrQuality created successfully", body))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrQuality"))
        }
    }

    @PostMapping("/update")
    fun updateZikrQuality(@RequestBody body: ZikrQualityDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrQualityService.updateZikrQuality(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrQuality updated successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrQuality not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrQuality(@RequestBody body: ZikrQualityDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrQualityService.deleteZikrQuality(body.id)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrQuality deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrQuality not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrQualityService.getUpdatedZikrQualities(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
