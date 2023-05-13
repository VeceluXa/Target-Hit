package com.danilovfa.targethit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.danilovfa.targethit.data.local.converters.ListCoordinateTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "levels")
data class LevelEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
//    @TypeConverters(ListCoordinateTypeConverter::class)
    val targets: List<CoordinateEntity>,
    @SerializedName("is_completed")
    val isCompleted: Boolean = false
)
