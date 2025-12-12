package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.IdRequestDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.dto.zikr.ZikrTagMapDtoRequest
import com.example.demo.presentation.service.zikr.ZikrTagMapService
import jakarta.validation.Valid
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
        val result = zikrTagMapService.getAllZikrTagMaps()
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK,
                "Fetched all ZikrTagMap records",
                result
            )
        )
    }

    @PostMapping("/getById")
    fun getZikrTagMapById(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val record = zikrTagMapService.getZikrTagMapById(body.id)

        return if (record != null) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTagMap found",
                    record
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTagMap not found"))
        }
    }

    @PostMapping("/add")
    fun createZikrTagMap(@Valid @RequestBody body: ZikrTagMapDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val created = zikrTagMapService.createZikrTagMap(body)

        return if (created) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        HttpStatus.CREATED,
                        "ZikrTagMap created successfully",
                        null
                    )
                )
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create ZikrTagMap"))
        }
    }

    @PostMapping("/update")
    fun updateZikrTagMap(@Valid @RequestBody body: ZikrTagMapDtoRequest)
            : ResponseEntity<ApiResponse<Any>> {

        val updated = zikrTagMapService.updateZikrTagMap(body)

        return if (updated) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTagMap updated successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTagMap not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikrTagMap(@Valid @RequestBody body: IdRequestDto)
            : ResponseEntity<ApiResponse<Any>> {

        val deleted = zikrTagMapService.deleteZikrTagMap(body.id)

        return if (deleted) {
            ResponseEntity.ok(
                ApiResponse.success(
                    HttpStatus.OK,
                    "ZikrTagMap deleted successfully",
                    null
                )
            )
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "ZikrTagMap not found or delete failed"))
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@Valid @RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {

        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val result = zikrTagMapService.getUpdatedZikrTagMaps(updatedAt)

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
