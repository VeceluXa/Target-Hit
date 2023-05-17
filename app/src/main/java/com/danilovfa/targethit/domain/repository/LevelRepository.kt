package com.danilovfa.targethit.domain.repository

import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.model.LevelItem

interface LevelRepository {
    suspend fun getLevelById(id: Int): Level
    suspend fun getLevels(): List<LevelItem>
    suspend fun setComplete(id: Int)
}