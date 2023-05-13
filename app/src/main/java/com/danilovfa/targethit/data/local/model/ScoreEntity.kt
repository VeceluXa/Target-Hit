package com.danilovfa.targethit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.danilovfa.targethit.data.local.converters.ListCoordinateTypeConverter
import com.danilovfa.targethit.data.local.converters.LocalDateTimeTypeConverter
import java.time.LocalDateTime

@Entity(tableName = "leaderboard")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int,
//    @TypeConverters(LocalDateTimeTypeConverter::class)
    val date: LocalDateTime,
//    @TypeConverters(ListCoordinateTypeConverter::class)
    val log: List<CoordinateEntity>
)
