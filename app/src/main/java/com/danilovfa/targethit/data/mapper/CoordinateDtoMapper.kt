package com.danilovfa.targethit.data.mapper

import com.danilovfa.targethit.data.remote.response.CoordinateDto
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.utils.Mapper

class CoordinateDtoMapper: Mapper<CoordinateDto, Coordinate> {
    override fun fromEntity(entity: CoordinateDto): Coordinate {
        return Coordinate(
            x = entity.x,
            y = entity.y,
            t = entity.t
        )
    }

    override fun fromDomain(domain: Coordinate): CoordinateDto {
        return CoordinateDto(
            x = domain.x,
            y = domain.y,
            t = domain.t
        )
    }
}