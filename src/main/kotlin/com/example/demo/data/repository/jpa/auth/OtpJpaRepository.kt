package com.example.demo.data.repository.jpa.auth

import com.example.demo.data.entity.OtpEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OtpJpaRepository : JpaRepository<OtpEntity, UUID> {

    // Use getReferenceById (lazy fetch) for better performance instead of full query when possible
    @Query("SELECT o FROM OtpEntity o WHERE o.id = :id")
    fun findOtpById(id: UUID): OtpEntity?

    @Modifying(clearAutomatically = true, flushAutomatically = false)
    @Query("DELETE FROM OtpEntity o WHERE o.id = :id")
    fun deleteOtpById(id: UUID): Int

    @Modifying(clearAutomatically = true, flushAutomatically = false)
    @Query("UPDATE OtpEntity o SET o.otpAttempts = o.otpAttempts + 1 WHERE o.id = :id")
    fun incrementOtpAttempts(id: UUID): Int
}