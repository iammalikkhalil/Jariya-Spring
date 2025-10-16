package com.example.demo.data.repository.auth


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
interface OtpRepository : JpaRepository<Otp, String> {

    @Query("SELECT o FROM Otp o WHERE o.id = :id")
    fun findOtpById(id: String): Otp?

    @Transactional
    @Modifying
    @Query("DELETE FROM Otp o WHERE o.id = :id")
    fun deleteOtpById(id: String)

    @Transactional
    @Modifying
    @Query("UPDATE Otp o SET o.otpAttempts = o.otpAttempts + 1 WHERE o.id = :id")
    fun incrementOtpAttempts(id: String)

    @Transactional
    @Modifying
    @Query("""
        INSERT INTO Otp (id, hashedOtp, otpAttempts, otpRequestedAt, expiresAt)
        VALUES (:id, :hashedOtp, 0, :otpRequestedAt, :expiresAt)
        ON CONFLICT (id) DO UPDATE 
        SET hashedOtp = :hashedOtp, otpAttempts = 0, otpRequestedAt = :otpRequestedAt, expiresAt = :expiresAt
    """)
    fun generateOtp(
        id: String,
        hashedOtp: String,
        otpRequestedAt: Instant,
        expiresAt: Instant
    )
}