package com.danilovfa.targethit.data.mapper

import com.danilovfa.targethit.data.local.model.CoordinateEntity
import com.danilovfa.targethit.domain.model.Coordinate

class CoordinateEntityMapper: Mapper<CoordinateEntity, Coordinate> {
    override fun fromEntity(entity: CoordinateEntity): Coordinate {
        return Coordinate(
            x = entity.x,
            y = entity.y,
            t = entity.timeInMs
        )
    }

    override fun fromDomain(domain: Coordinate): CoordinateEntity {
        return CoordinateEntity(
            x = domain.x,
            y = domain.y,
            timeInMs = domain.t
        )
    }
}