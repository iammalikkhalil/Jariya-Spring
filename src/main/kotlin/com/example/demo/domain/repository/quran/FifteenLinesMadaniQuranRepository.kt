package com.example.demo.domain.repository.quran

import com.example.demo.domain.model.quran.FifteenLinesMadaniQuranModel

interface FifteenLinesMadaniQuranRepository {
     fun getLine(pageNumber: Int, lineNumber: Int): FifteenLinesMadaniQuranModel?
     fun getPage(pageNumber: Int): List<FifteenLinesMadaniQuranModel>
     fun insertLine(pageNumber: Int, lineNumber: Int, textAr: String): FifteenLinesMadaniQuranModel?
     fun updateLine(pageNumber: Int, lineNumber: Int, newTextAr: String): FifteenLinesMadaniQuranModel?
     fun deleteLine(pageNumber: Int, lineNumber: Int): Boolean

    // Get the last line based on pageNumber and lineNumber
     fun getLastLine(): FifteenLinesMadaniQuranModel?

     fun bulkInsertLines(lines: List<FifteenLinesMadaniQuranModel>): List<FifteenLinesMadaniQuranModel>
}
