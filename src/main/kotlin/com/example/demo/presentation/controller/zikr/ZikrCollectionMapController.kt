package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrCollectionMapDtoRequest
import com.example.demo.presentation.service.zikr.ZikrCollectionMapService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/zikrCollectionMap")
class ZikrCollectionMapController(
    private val zikrCollectionMapService: ZikrCollectionMapService
) {

    @GetMapping("/getAll")
    fun getAllZikrCollectionMaps(): ResponseEntity<ApiResponse<Any>> {
        val zikrCollectionMaps = zikrCollectionMapService.getAllZikrCollectionMaps()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all ZikrCollectionMap records", zikrCollectionMaps))
    }

    @PostMapping("/getById")
    fun getZikrCollectionMapById(@RequestBody body: ZikrCollectionMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val zikrCollectionMap = zikrCollectionMapService.getZikrCollectionMapById(body.id)
        return if (zikrCollectionMap != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrCollectionMap found", zikrCollectionMap))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrCollectionMap not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrCollectionMap(@RequestBody body: ZikrCollectionMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val result = zikrCollectionMapService.createZikrCollectionMap(body)
        return if (result) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "ZikrCollectionMap created successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrCollectionMap"))
        }
    }

    @PostMapping("/update")
    fun updateZikrCollectionMap(@RequestBody body: ZikrCollectionMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrCollectionMapService.updateZikrCollectionMap(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrCollectionMap updated successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrCollectionMap not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrCollectionMap(@RequestBody body: ZikrCollectionMapDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrCollectionMapService.deleteZikrCollectionMap(body.id)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "ZikrCollectionMap deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrCollectionMap not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrCollectionMapService.getUpdatedZikrCollectionMaps(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
