package com.danilovfa.targethit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "leaderboard")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val levelId: Int,
    val score: Int,
    val date: LocalDateTime,
    val log: List<CoordinateEntity>
)
