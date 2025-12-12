package com.example.demo.data.entity

import jakarta.persistence.*
import java.io.Serializable
import java.util.Objects
import java.util.UUID


@Embeddable
class ReferralPathId(

    @Column(name = "ancestor", nullable = false)
    var ancestor: UUID,

    @Column(name = "descendant", nullable = false)
    var descendant: UUID

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ReferralPathId) return false
        return ancestor == other.ancestor && descendant == other.descendant
    }

    override fun hashCode(): Int =
        Objects.hash(ancestor, descendant)
}
