package com.danilovfa.targethit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danilovfa.targethit.data.local.model.ScoreEntity
import com.danilovfa.targethit.utils.Constants.Companion.LEADERBOARD_SIZE

    @Dao
    interface LeaderboardDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun setScore(score: ScoreEntity)

        @Query("SELECT * FROM leaderboard WHERE levelId = :id ORDER BY score DESC LIMIT $LEADERBOARD_SIZE")
        suspend fun getTopScores(id: Int): List<ScoreEntity>
    }