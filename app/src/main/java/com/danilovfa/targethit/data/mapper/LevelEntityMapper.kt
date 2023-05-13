package com.danilovfa.targethit.data.mapper

import com.danilovfa.targethit.data.local.model.LevelEntity
import com.danilovfa.targethit.domain.model.Level

class LevelEntityMapper: Mapper<LevelEntity, Level> {
    val coordinateMapper = CoordinateEntityMapper()
    override fun fromEntity(entity: LevelEntity): Level {
        val targets = entity.targets.map { coordinateEntity ->
            coordinateMapper.fromEntity(coordinateEntity)
        }
        return Level(
            id = entity.id,
            targets = targets,
            isCompleted = entity.isCompleted
        )
    }

    override fun fromDomain(domain: Level): LevelEntity {
        val targets = domain.targets.map { coordinate ->
            coordinateMapper.fromDomain(coordinate)
        }
        return LevelEntity(
            id = domain.id,
            targets = targets,
            isCompleted = domain.isCompleted
        )
    }

}