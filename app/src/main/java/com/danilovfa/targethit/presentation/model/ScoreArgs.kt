package com.danilovfa.targethit.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScoreArgs(
    val levelId: Int,
    val score: Int,
    val date: String,
    val log: List<CoordinateArgs>
): Parcelable
