package com.danilovfa.targethit.presentation.model

import java.io.Serializable

data class ScoreArgs(
    val levelId: Int,
    val score: Int,
    val date: String,
    val log: List<CoordinateArgs>
): Serializable
