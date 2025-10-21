package com.example.demo.data.mapper.referral

import com.example.demo.data.entity.ReferralPathEntity
import com.example.demo.data.entity.ReferralPathId
import com.example.demo.data.entity.UserEntity
import com.example.demo.domain.model.referral.ReferralModel

fun ReferralPathEntity.toModel(): ReferralModel =
    ReferralModel(
        ancestor = ancestor.id.toString(),
        descendant = descendant.id.toString(),
        level = level
    )

fun ReferralModel.toEntity(
    ancestorEntity: UserEntity,
    descendantEntity: UserEntity
): ReferralPathEntity =
    ReferralPathEntity(
        id = ReferralPathId(
            ancestor = ancestorEntity.id,
            descendant = descendantEntity.id
        ),
        ancestor = ancestorEntity,
        descendant = descendantEntity,
        level = level
    )
