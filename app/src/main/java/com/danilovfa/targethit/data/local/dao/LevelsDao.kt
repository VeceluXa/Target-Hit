package com.danilovfa.targethit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danilovfa.targethit.data.local.model.LevelEntity

@Dao
interface LevelsDao {
    @Query("SELECT * FROM levels WHERE id = :id")
    suspend fun getLevel(id: Int): LevelEntity

    @Query("SELECT * FROM levels")
    suspend fun getLevels(): List<LevelEntity>

    @Query("UPDATE levels SET isCompleted = true WHERE id = :id")
    fun setLevelCompleted(id: Int)

    @Query("SELECT isCompleted FROM levels WHERE id = :id")
    fun isCompleted(id: Int): Boolean

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun addRow(level: LevelEntity)
}