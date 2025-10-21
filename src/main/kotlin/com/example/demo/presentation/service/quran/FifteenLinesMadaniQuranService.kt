package com.example.demo.presentation.service.quran

import com.example.demo.domain.repository.quran.FifteenLinesMadaniQuranRepository
import com.example.demo.presentation.dto.quran.FifteenLinesMadaniQuranDto
import com.example.demo.presentation.dto.quran.toDomain
import org.springframework.stereotype.Service

@Service
class FifteenLinesMadaniQuranService(
    private val quranRepository: FifteenLinesMadaniQuranRepository
) {

    fun getLine(pageNumber: Int, lineNumber: Int) =
        quranRepository.getLine(pageNumber, lineNumber)

    fun getPage(pageNumber: Int) =
        quranRepository.getPage(pageNumber)

    fun insertLine(body: FifteenLinesMadaniQuranDto) =
        quranRepository.insertLine(body.pageNumber, body.lineNumber, body.textAr)

    fun bulkInsertLines(lines: List<FifteenLinesMadaniQuranDto>) =
        quranRepository.bulkInsertLines(lines.map {
            it.toDomain()
        })

    fun updateLine(body: FifteenLinesMadaniQuranDto) =
        quranRepository.updateLine(body.pageNumber, body.lineNumber, body.textAr)

    fun deleteLine(pageNumber: Int, lineNumber: Int) =
        quranRepository.deleteLine(pageNumber, lineNumber)

    fun getLastLine() =
        quranRepository.getLastLine()
}
