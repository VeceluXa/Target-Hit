package com.danilovfa.targethit.data.repository

import com.danilovfa.targethit.data.local.dao.LevelsDao
import com.danilovfa.targethit.data.local.model.CoordinateEntity
import com.danilovfa.targethit.data.local.model.LevelEntity
import com.danilovfa.targethit.data.mapper.LevelEntityMapper
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.repository.LevelRepository
import javax.inject.Inject

class LevelRepositoryImpl @Inject constructor(
    private val levelsDao: LevelsDao): LevelRepository {
    val levelMapper = LevelEntityMapper()
    override suspend fun getLevelById(id: Int): Level {
        val entity = levelsDao.getLevel(id)
        return levelMapper.fromEntity(entity)
    }

    override suspend fun getLevels(): List<Level> {
        levelsDao.addRow(LevelEntity(
            0,
            listOf(CoordinateEntity(54, 120, 0),
                CoordinateEntity(500, 324, 5000),
                CoordinateEntity(-1, -1, 10000)),
            true
        ))
        levelsDao.addRow(LevelEntity(
            1,
            listOf(),
            false
        ))
        return levelsDao.getLevels().map { entity ->
            levelMapper.fromEntity(entity)
        }
    }

    override suspend fun setComplete(id: Int) {
        levelsDao.setLevelCompleted(id)
    }
}