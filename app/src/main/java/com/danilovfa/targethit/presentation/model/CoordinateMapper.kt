package com.danilovfa.targethit.presentation.model

import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.utils.Constants.Companion.DOMAIN_GAME_FIELD_HEIGHT
import com.danilovfa.targethit.utils.Constants.Companion.DOMAIN_GAME_FIELD_WIDTH

class CoordinateMapper(
    private val viewWidth: Int,
    private val viewHeight: Int,
) {
    fun fromView(coordinate: Coordinate): Coordinate {
        return Coordinate(
            x = coordinate.x * DOMAIN_GAME_FIELD_WIDTH / viewWidth,
            y = coordinate.y * DOMAIN_GAME_FIELD_HEIGHT / viewHeight,
            t = coordinate.t
        )
    }

    fun fromData(coordinate: Coordinate): Coordinate {
        return Coordinate(
            x = coordinate.x * viewWidth / DOMAIN_GAME_FIELD_WIDTH,
            y = coordinate.y * viewHeight / DOMAIN_GAME_FIELD_HEIGHT,
            t = coordinate.t
        )
    }
}