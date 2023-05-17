package com.danilovfa.targethit.data.mapper

import com.danilovfa.targethit.data.remote.response.LevelDto
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.utils.Mapper

class LevelDtoMapper: Mapper<LevelDto, Level> {
    private val coordinateDtoMapper = CoordinateDtoMapper()

    override fun fromEntity(entity: LevelDto): Level {
        return Level(
            id = entity.id,
            targets = entity.targets.map { coordinateDto ->
                coordinateDtoMapper.fromEntity(coordinateDto)
            }
        )
    }

    override fun fromDomain(domain: Level): LevelDto {
        return LevelDto(
            id = domain.id,
            targets = domain.targets.map { coordinate ->
                coordinateDtoMapper.fromDomain(coordinate)
            }
        )
    }
}