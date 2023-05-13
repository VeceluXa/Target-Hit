package com.danilovfa.targethit.data.repository

import com.danilovfa.targethit.data.local.dao.LevelsDao
import com.danilovfa.targethit.data.mapper.LevelEntityMapper
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.repository.LevelRepository
import javax.inject.Inject

class LevelRepositoryImpl @Inject constructor(
    private val levelsDao: LevelsDao): LevelRepository {
    val levelMapper = LevelEntityMapper()
    override suspend fun getLevelById(id: Int): Level {
        TODO("Not yet implemented")
    }

    override suspend fun getLevels(): List<Level> {
        return levelsDao.getLevels().map { entity ->
            levelMapper.fromEntity(entity)
        }
    }

    override fun setComplete(id: Int) {
        TODO("Not yet implemented")
    }
}