package com.danilovfa.targethit.data.mapper

import com.danilovfa.targethit.data.local.model.LevelEntity
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.model.LevelItem
import com.danilovfa.targethit.utils.Mapper

class LevelEntityMapper: Mapper<LevelEntity, LevelItem> {
    override fun fromEntity(entity: LevelEntity): LevelItem {
        return LevelItem(
            id = entity.id,
            isCompleted = entity.isCompleted
        )
    }

    override fun fromDomain(domain: LevelItem): LevelEntity {
        return LevelEntity(
            id = domain.id,
            isCompleted = domain.isCompleted
        )
    }

}