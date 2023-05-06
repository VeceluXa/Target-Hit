package com.danilovfa.targethit.domain.model

data class Level(
    val id: Int,
    val targets: List<Coordinate>,
    val isCompleted: Boolean
)
