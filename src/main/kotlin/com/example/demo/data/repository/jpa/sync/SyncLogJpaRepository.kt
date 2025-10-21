package com.example.demo.data.repository.jpa.sync

import com.example.demo.data.entity.SyncLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface SyncLogJpaRepository : JpaRepository<SyncLogEntity, UUID> {

    fun findByTName(tName: String): SyncLogEntity?

    fun findByUpdatedAtAfter(updatedAt: Instant): List<SyncLogEntity>
}
