package com.danilovfa.targethit.domain.model

import java.time.LocalDateTime

data class Score(
    val player: Player,
    val levelId: Int,
    val score: Int,
    val date: LocalDateTime,
    val log: List<Coordinate>
)
