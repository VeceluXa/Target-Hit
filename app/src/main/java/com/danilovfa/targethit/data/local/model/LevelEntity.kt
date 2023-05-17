package com.danilovfa.targethit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "levels")
data class LevelEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("is_completed")
    val isCompleted: Boolean = false
)
