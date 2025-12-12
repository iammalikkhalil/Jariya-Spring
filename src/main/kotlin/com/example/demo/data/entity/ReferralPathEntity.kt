package com.example.demo.data.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "referral_paths",
    indexes = [
        Index(name = "idx_referralpath_ancestor", columnList = "ancestor"),
        Index(name = "idx_referralpath_descendant", columnList = "descendant")
    ]
)
class ReferralPathEntity(

    @EmbeddedId
    var id: ReferralPathId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ancestor")
    @JoinColumn(name = "ancestor", referencedColumnName = "id", nullable = false)
    var ancestor: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("descendant")
    @JoinColumn(name = "descendant", referencedColumnName = "id", nullable = false)
    var descendant: UserEntity,

    @Column(name = "level", nullable = false)
    var level: Int
)
