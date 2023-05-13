package com.danilovfa.targethit.data.local.converters

import androidx.room.TypeConverter
import com.danilovfa.targethit.data.local.model.CoordinateEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListCoordinateTypeConverter {
    @TypeConverter
    fun fromCoordinate(coord: List<CoordinateEntity>): String {
        return Gson().toJson(coord)
    }

    @TypeConverter
    fun fromString(json: String): List<CoordinateEntity> {
        val type = object : TypeToken<List<CoordinateEntity>>() {}.type
        return Gson().fromJson(json, type)
    }
}