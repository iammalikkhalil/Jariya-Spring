package com.example.demo.data.repository.jpa.auth


import com.example.demo.data.entity.OtpEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface OtpJpaRepository : JpaRepository<OtpEntity, UUID> {

    @Query("SELECT o FROM OtpEntity o WHERE o.id = :id")
    fun findOtpById(id: UUID): OtpEntity?

    @Transactional
    @Modifying
    @Query("DELETE FROM OtpEntity o WHERE o.id = :id")
    fun deleteOtpById(id: UUID): Int

    @Transactional
    @Modifying
    @Query("UPDATE OtpEntity o SET o.otpAttempts = o.otpAttempts + 1 WHERE o.id = :id")
    fun incrementOtpAttempts(id: UUID): Int
}
