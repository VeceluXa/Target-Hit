package com.danilovfa.targethit.data.repository

import com.danilovfa.targethit.data.local.dao.LevelsDao
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.repository.LevelRepository
import javax.inject.Inject

class LevelRepositoryImpl @Inject constructor(
    levelsDao: LevelsDao): LevelRepository {
    override suspend fun getLevel(id: Int): Level {
        TODO("Not yet implemented")
    }

    override fun setComplete(id: Int) {
        TODO("Not yet implemented")
    }
}