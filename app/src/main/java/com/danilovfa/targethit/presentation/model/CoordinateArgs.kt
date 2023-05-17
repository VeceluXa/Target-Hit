package com.danilovfa.targethit.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoordinateArgs(
    val x: Int,
    val y: Int,
    val t: Int
): Parcelable
