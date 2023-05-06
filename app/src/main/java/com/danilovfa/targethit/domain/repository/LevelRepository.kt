package com.danilovfa.targethit.domain.repository

import com.danilovfa.targethit.domain.model.Level

interface LevelRepository {
    suspend fun getLevel(id: Int): Level
    fun setComplete(id: Int)
}