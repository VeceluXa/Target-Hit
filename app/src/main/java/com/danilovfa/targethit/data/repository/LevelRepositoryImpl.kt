package com.danilovfa.targethit.data.repository

import com.danilovfa.targethit.data.local.dao.LevelsDao
import com.danilovfa.targethit.data.local.model.LevelEntity
import com.danilovfa.targethit.data.mapper.LevelDtoMapper
import com.danilovfa.targethit.data.mapper.LevelEntityMapper
import com.danilovfa.targethit.data.remote.LevelsAPI
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.model.LevelItem
import com.danilovfa.targethit.domain.repository.LevelRepository
import javax.inject.Inject

class LevelRepositoryImpl @Inject constructor(
    private val levelsAPI: LevelsAPI,
    private val levelsDao: LevelsDao): LevelRepository {

    private val levelEntityMapper = LevelEntityMapper()
    private val levelDtoMapper = LevelDtoMapper()

    override suspend fun getLevelById(id: Int): Level {
        val entity = levelsAPI.getLevel(id)
        return levelDtoMapper.fromEntity(entity.body()!!)
    }

    override suspend fun getLevels(): List<LevelItem> {
        val amount = levelsAPI.getAmountOfLevels().body()!!
        val levelsInDb = levelsDao.getLevels()

        if (levelsDao.getLevels().size < amount) {
            for (i in levelsInDb.size until amount - levelsInDb.size) {
                levelsDao.addRow(LevelEntity(i, false))
            }
        }

        return levelsDao.getLevels().map { levelEntity ->
            levelEntityMapper.fromEntity(levelEntity)
        }
    }

    override suspend fun setComplete(id: Int) {
        levelsDao.setLevelCompleted(id)
    }
}