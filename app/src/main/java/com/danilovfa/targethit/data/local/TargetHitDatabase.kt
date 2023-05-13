package com.danilovfa.targethit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danilovfa.targethit.data.local.converters.ListCoordinateTypeConverter
import com.danilovfa.targethit.data.local.converters.LocalDateTimeTypeConverter
import com.danilovfa.targethit.data.local.dao.LeaderboardDao
import com.danilovfa.targethit.data.local.dao.LevelsDao
import com.danilovfa.targethit.data.local.model.LevelEntity
import com.danilovfa.targethit.data.local.model.ScoreEntity

@Database(
    entities = [LevelEntity::class, ScoreEntity::class],
    version = 1
)
@TypeConverters(ListCoordinateTypeConverter::class, LocalDateTimeTypeConverter::class)
abstract class TargetHitDatabase: RoomDatabase() {
    abstract val leaderboardDao: LeaderboardDao
    abstract val levelsDao: LevelsDao

    companion object {
        const val DATABASE_NAME = "TargetHit"
    }
}