package com.example.demo.data.entity

import jakarta.persistence.*
import java.io.Serializable
import java.util.*


@Entity
@Table(name = "referral_paths")
data class ReferralPathEntity(

    @EmbeddedId
    val id: ReferralPathId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ancestor")
    @JoinColumn(name = "ancestor", referencedColumnName = "id", nullable = false)
    val ancestor: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("descendant")
    @JoinColumn(name = "descendant", referencedColumnName = "id", nullable = false)
    val descendant: UserEntity,

    @Column(name = "level", nullable = false)
    val level: Int
)

@Embeddable
data class ReferralPathId(
    @Column(name = "ancestor")
    val ancestor: UUID = UUID(0, 0),

    @Column(name = "descendant")
    val descendant: UUID = UUID(0, 0)
) : Serializable
