package com.danilovfa.targethit.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeTypeConverter {
    @TypeConverter
    fun fromDate(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun fromString(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString)
    }

}