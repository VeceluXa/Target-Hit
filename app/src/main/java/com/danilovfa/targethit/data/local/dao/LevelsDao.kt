package com.danilovfa.targethit.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.danilovfa.targethit.data.local.model.LevelEntity

@Dao
interface LevelsDao {
    @Query("SELECT * FROM levels WHERE id = :id")
    suspend fun getLevel(id: Int): LevelEntity

    @Query("UPDATE levels SET isCompleted = true WHERE id = :id")
    fun setLevelCompleted(id: Int)

    @Query("SELECT isCompleted FROM levels WHERE id = :id")
    fun isCompleted(id: Int): Boolean
}