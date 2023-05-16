package com.danilovfa.targethit.presentation.mapper

import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.presentation.model.ScoreArgs
import com.danilovfa.targethit.utils.Mapper
import java.time.LocalDateTime
import kotlin.math.log

class ScoreArgsMapper: Mapper<ScoreArgs, Score> {
    private val coordinateArgsMapper = CoordinateArgsMapper()

    override fun fromEntity(entity: ScoreArgs): Score {
        return Score(
            levelId = entity.levelId,
            score = entity.score,
            date = LocalDateTime.parse(entity.date),
            log = entity.log.map { coordinateArgs ->
                coordinateArgsMapper.fromEntity(coordinateArgs)
            }
        )
    }

    override fun fromDomain(domain: Score): ScoreArgs {
        return ScoreArgs(
            levelId = domain.levelId,
            score = domain.score,
            date = domain.date.toString(),
            log = domain.log.map { coordinate ->
                coordinateArgsMapper.fromDomain(coordinate)
            }
        )
    }
}