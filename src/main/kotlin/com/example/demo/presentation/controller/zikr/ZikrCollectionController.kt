package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrCollectionDtoRequest
import com.example.demo.presentation.service.zikr.ZikrCollectionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrCollection")
class ZikrCollectionController(
    private val zikrCollectionService: ZikrCollectionService
) {

    @GetMapping("/getAll")
    fun getAllZikrCollections(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrCollectionService.getAllZikrCollections()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrCollection records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrCollectionById(@Valid @RequestBody body: ZikrCollectionDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val record = zikrCollectionService.getZikrCollectionById(body.id!!)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrCollection found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrCollection not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrCollection(@Valid @RequestBody body: ZikrCollectionDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrCollectionService.createZikrCollection(body)

        return if (success) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrCollection created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrCollection"))
        }
    }

    @PostMapping("/update")
    fun updateZikrCollection(@Valid @RequestBody body: ZikrCollectionDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrCollectionService.updateZikrCollection(body)

        return if (success) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrCollection updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrCollection not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrCollection(@Valid @RequestBody body: ZikrCollectionDtoRequest): ResponseEntity<ApiResponse<Any>> {
        val success = zikrCollectionService.deleteZikrCollection(body.id!!)

        return if (success) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrCollection deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrCollection not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrCollectionService.getUpdatedZikrCollections(updatedAt)

            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "Updated ZikrCollection records fetched",
                    result
                )
            )
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest().body(
                ApiResponse.error(
                    HttpStatus.BAD_REQUEST,
                    "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"
                )
            )
        }
    }
}
