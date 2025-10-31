package com.example.demo.presentation.controller.quran


import com.example.demo.presentation.dto.ApiResponse
import com.example.demo.presentation.dto.quran.QuranAyahRequestDTO
import com.example.demo.presentation.service.quran.QuranAyahService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quran/ayahs")
class QuranAyahController(private val quranAyahService: QuranAyahService) {

    // Endpoint to fetch Ayahs by Surah and range
    @PostMapping("/fetch")
    fun getAyahsBySurahAndRange(
        @RequestBody request: QuranAyahRequestDTO
    ): ResponseEntity<ApiResponse<Any>> {
        val ayahs = quranAyahService.compareAyahsWithRawLine(request)
           return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "fetched", ayahs))
    }
}
