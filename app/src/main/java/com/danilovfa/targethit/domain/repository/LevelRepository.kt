package com.danilovfa.targethit.domain.repository

import com.danilovfa.targethit.domain.model.Level

interface LevelRepository {
    suspend fun getLevelById(id: Int): Level
    suspend fun getLevels(): List<Level>
    fun setComplete(id: Int)
}