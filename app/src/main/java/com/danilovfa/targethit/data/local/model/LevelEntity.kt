package com.danilovfa.targethit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "levels")
data class LevelEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val targets: List<CoordinateEntity>,
    val isCompleted: Boolean = false
)
