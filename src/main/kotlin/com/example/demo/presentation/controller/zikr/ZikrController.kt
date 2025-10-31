package com.example.demo.presentation.controller.zikr

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.zikr.CreateZikrDto
import com.example.demo.presentation.dto.zikr.CsvZikrDto
import com.example.demo.presentation.dto.zikr.GetZikrByIdDto
import com.example.demo.presentation.dto.zikr.TimeDto
import com.example.demo.presentation.service.zikr.ZikrService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api/zikr")
class ZikrController(
    private val zikrService: ZikrService
) {

    @GetMapping("/getAll")
    fun getAllZikrs(): ResponseEntity<ApiResponse<Any>> {
        val zikrs = zikrService.getAllZikrs()
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Fetched all Zikr records", zikrs))
    }

    @PostMapping("/getById")
    fun getZikrById(@RequestBody body: GetZikrByIdDto): ResponseEntity<ApiResponse<Any>> {
        val zikr = zikrService.getZikrById(body.id)
        return if (zikr != null) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikr))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "Zikr not found"))
        }
    }

    @PostMapping("/add")
    fun createZikr(@RequestBody body: CreateZikrDto): ResponseEntity<ApiResponse<Any>> {
        val zikr = zikrService.createZikr(body)
        return if (zikr != null) {
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "Zikr created successfully", zikr))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create Zikr"))
        }
    }

    @PostMapping("/update")
    fun updateZikr(@RequestBody body: CreateZikrDto): ResponseEntity<ApiResponse<Any>> {
        val success = zikrService.updateZikr(body)
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr updated successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "Zikr not found or update failed"))
        }
    }

    @PostMapping("/deleteById")
    fun deleteZikr(@RequestBody body: CreateZikrDto): ResponseEntity<ApiResponse<Any>> {
        val success = zikrService.deleteZikr(body.id ?: "")
        return if (success) {
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr deleted successfully", null))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "Zikr not found or delete failed"))
        }
    }

    @PostMapping("/bulkInsertZikrs")
    fun bulkInsertZikrs(@RequestBody rows: List<CsvZikrDto>): ResponseEntity<Any> {
        val result = zikrService.bulkInsertZikrs(rows)
        return if (result.successfulEntries!= 0) {
            ResponseEntity.status(HttpStatus.CREATED).body(result)
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result)
        }
    }

    @PostMapping("/getUpdated")
    fun getUpdated(@RequestBody body: TimeDto): ResponseEntity<ApiResponse<Any>> {
        return try {
            val updatedAt = Instant.parse(body.updatedAt)
            val zikrs = zikrService.getUpdatedZikrs(updatedAt)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Zikr found", zikrs))
        } catch (e: DateTimeParseException) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid datetime format. Use ISO-8601 like 2025-09-30T12:34:56Z"))
        }
    }
}
