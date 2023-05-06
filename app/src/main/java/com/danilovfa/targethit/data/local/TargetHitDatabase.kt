package com.danilovfa.targethit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danilovfa.targethit.data.local.dao.LeaderboardDao
import com.danilovfa.targethit.data.local.dao.LevelsDao
import com.danilovfa.targethit.data.local.model.LevelEntity
import com.danilovfa.targethit.data.local.model.ScoreEntity

@Database(
    entities = [LevelEntity::class, ScoreEntity::class],
    version = 1
)
abstract class TargetHitDatabase: RoomDatabase() {
    abstract val leaderboardDao: LeaderboardDao
    abstract val levelsDao: LevelsDao

    companion object {
        const val DATABASE_NAME = "TargetHit"
    }
}