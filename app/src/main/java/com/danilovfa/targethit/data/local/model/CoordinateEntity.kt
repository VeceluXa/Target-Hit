package com.danilovfa.targethit.data.local.model

import com.google.gson.annotations.SerializedName

data class CoordinateEntity(
    val x: Int,
    val y: Int,
    @SerializedName("time_in_ms")
    val timeInMs: Int
)