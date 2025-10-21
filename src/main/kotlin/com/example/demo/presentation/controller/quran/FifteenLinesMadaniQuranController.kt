package com.example.demo.presentation.controller.quran

import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.quran.FifteenLinesMadaniQuranDto
import com.example.demo.presentation.service.quran.FifteenLinesMadaniQuranService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quran")
class FifteenLinesMadaniQuranController(
    private val quranService: FifteenLinesMadaniQuranService
) {

    @GetMapping("/getLine")
    fun getLine(
        @RequestParam pageNumber: Int?,
        @RequestParam lineNumber: Int?
    ): ResponseEntity<ApiResponse<Any>> {
        if (pageNumber == null || lineNumber == null) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "pageNumber and lineNumber are required"))
        }

        val line = quranService.getLine(pageNumber, lineNumber)
        return if (line != null)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Line fetched successfully", line))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "Line not found"))
    }

    @GetMapping("/getPage")
    fun getPage(@RequestParam pageNumber: Int?): ResponseEntity<ApiResponse<Any>> {
        if (pageNumber == null) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "pageNumber is required"))
        }

        val lines = quranService.getPage(pageNumber)
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Page fetched successfully", lines))
    }

    @PostMapping("/add")
    fun insertLine(@RequestBody body: FifteenLinesMadaniQuranDto): ResponseEntity<ApiResponse<Any>> {
        val inserted = quranService.insertLine(body)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, "Line inserted successfully", inserted))
    }

    @PostMapping("/bulkInsertLines")
    fun bulkInsertLines(@RequestBody lines: List<FifteenLinesMadaniQuranDto>): ResponseEntity<ApiResponse<Any>> {
        val inserted = quranService.bulkInsertLines(lines)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, "Bulk insert completed", inserted))
    }

    @PostMapping("/update")
    fun updateLine(@RequestBody body: FifteenLinesMadaniQuranDto): ResponseEntity<ApiResponse<Any>> {
        val updated = quranService.updateLine(body)
        return if (updated != null)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Line updated successfully", updated))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "Line not found or update failed"))
    }

    @PostMapping("/delete")
    fun deleteLine(@RequestBody body: FifteenLinesMadaniQuranDto): ResponseEntity<ApiResponse<Any>> {
        val deleted = quranService.deleteLine(body.pageNumber, body.lineNumber)
        return if (deleted)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Line deleted successfully", null))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "Line not found or delete failed"))
    }

    @GetMapping("/getLastLine")
    fun getLastLine(): ResponseEntity<ApiResponse<Any>> {
        val line = quranService.getLastLine()
        return if (line != null)
            ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "Last line fetched successfully", line))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, "No lines found in the database"))
    }
}
