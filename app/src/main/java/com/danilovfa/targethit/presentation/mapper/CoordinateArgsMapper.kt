package com.danilovfa.targethit.presentation.mapper

import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.presentation.model.CoordinateArgs
import com.danilovfa.targethit.utils.Mapper

class CoordinateArgsMapper: Mapper<CoordinateArgs, Coordinate> {
    override fun fromEntity(entity: CoordinateArgs): Coordinate {
        return Coordinate(
            x = entity.x,
            y = entity.y,
            t = entity.t
        )
    }

    override fun fromDomain(domain: Coordinate): CoordinateArgs {
        return CoordinateArgs(
            x = domain.x,
            y = domain.y,
            t = domain.t
        )
    }
}