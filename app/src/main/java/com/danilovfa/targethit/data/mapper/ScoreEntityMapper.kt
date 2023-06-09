package com.danilovfa.targethit.data.mapper

import com.danilovfa.targethit.data.local.model.ScoreEntity
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.utils.Mapper

class ScoreEntityMapper: Mapper<ScoreEntity, Score> {
    private val coordinateEntityMapper = CoordinateEntityMapper()
    override fun fromEntity(entity: ScoreEntity): Score {
        return Score(
            levelId = entity.levelId,
            score = entity.score,
            date = entity.date,
            log = entity.log.map { coordinateEntity ->
                coordinateEntityMapper.fromEntity(coordinateEntity)
            }
        )
    }

    override fun fromDomain(domain: Score): ScoreEntity {
        return ScoreEntity(
            levelId = domain.levelId,
            score = domain.score,
            date = domain.date,
            log = domain.log.map { coordinate ->
                coordinateEntityMapper.fromDomain(coordinate)
            }
        )
    }
}