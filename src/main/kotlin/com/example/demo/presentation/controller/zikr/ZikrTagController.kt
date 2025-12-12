package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.IdRequestDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrTagDtoRequest
import com.example.demo.presentation.service.zikr.ZikrTagService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikrTag")
class ZikrTagController(
    private val zikrTagService: ZikrTagService
) {

    @GetMapping("/getAll")
    fun getAllZikrTags(): ResponseEntity<ApiResponse<Any>> {
        val result = zikrTagService.getAllZikrTags()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrTag records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrTagById(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val record = zikrTagService.getZikrTagById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTag found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTag not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrTag(@Valid @RequestBody body: ZikrTagDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val created = zikrTagService.createZikrTag(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrTag created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrTag"))
        }
    }

    @PostMapping("/update")
    fun updateZikrTag(@Valid @RequestBody body: ZikrTagDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val updated = zikrTagService.updateZikrTag(body)

        return if (updated) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTag updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTag not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrTag(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val deleted = zikrTagService.deleteZikrTag(body.id)

        return if (deleted) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTag deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTag not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {

            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrTagService.getUpdatedZikrTags(updatedAt)

            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "Zikr found",
                    result
                )
            )

        } catch (e: DateTimeParseException) {

            ResponseEntity.badRequest()
                .body(
                    ApiResponse.error(
                        HttpStatus.BAD_REQUEST,
                        "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"
                    )
                )
        }
    }
}
