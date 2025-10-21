package com.example.demo.data.repository.jpa.quran

import com.example.demo.data.entity.FifteenLinesMadaniQuranEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface FifteenLinesMadaniQuranJpaRepository : JpaRepository<FifteenLinesMadaniQuranEntity, Long> {

    fun findByPageNumberAndLineNumber(pageNumber: Int, lineNumber: Int): FifteenLinesMadaniQuranEntity?

    fun findAllByPageNumberOrderByLineNumberAsc(pageNumber: Int): List<FifteenLinesMadaniQuranEntity>

    @Modifying
    @Query(
        "UPDATE FifteenLinesMadaniQuranEntity f SET f.textAr = :textAr, f.updatedAt = :updatedAt " +
                "WHERE f.pageNumber = :pageNumber AND f.lineNumber = :lineNumber"
    )
    fun updateLine(
        @Param("pageNumber") pageNumber: Int,
        @Param("lineNumber") lineNumber: Int,
        @Param("textAr") textAr: String,
        @Param("updatedAt") updatedAt: Instant
    ): Int

    @Modifying
    @Query(
        "DELETE FROM FifteenLinesMadaniQuranEntity f WHERE f.pageNumber = :pageNumber AND f.lineNumber = :lineNumber"
    )
    fun deleteLine(
        @Param("pageNumber") pageNumber: Int,
        @Param("lineNumber") lineNumber: Int
    ): Int

    @Query(
        "SELECT f FROM FifteenLinesMadaniQuranEntity f ORDER BY f.pageNumber DESC, f.lineNumber DESC"
    )
    fun findTopByOrderByPageNumberDescLineNumberDesc(): FifteenLinesMadaniQuranEntity?
}
