package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrTagMapDtoRequest
import com.example.demo.presentation.service.zikr.ZikrTagMapService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrTagMap")
class ZikrTagMapController(
    private val zikrTagMapService: ZikrTagMapService
) {

    @GetMapping("/getAll")
    fun getAllZikrTagMaps(): ResponseEntity<ApiResponse<Any>> {
        val zikrTagMaps = zikrTagMapService.getAllZikrTagMaps()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrTagMap records", zikrTagMaps))
    }

    @PostMapping("/getById")
    fun getZikrTagMapById(@RequestBody body: ZikrTagMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val zikrTagMap = zikrTagMapService.getZikrTagMapById(body.id)
        return if (zikrTagMap != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrTagMap found", zikrTagMap))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTagMap not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrTagMap(@RequestBody body: ZikrTagMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val result = zikrTagMapService.createZikrTagMap(body)
        return if (result) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrTagMap created successfully", body))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrTagMap"))
        }
    }

    @PostMapping("/update")
    fun updateZikrTagMap(@RequestBody body: ZikrTagMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrTagMapService.updateZikrTagMap(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrTagMap updated successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTagMap not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrTagMap(@RequestBody body: ZikrTagMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrTagMapService.deleteZikrTagMap(body.id)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrTagMap deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTagMap not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrTagMapService.getUpdatedZikrTagMaps(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
