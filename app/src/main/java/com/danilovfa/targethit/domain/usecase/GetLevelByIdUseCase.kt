package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.repository.LevelRepository

class GetLevelById(private val levelRepository: LevelRepository) {
    suspend fun execute(id: Int): Level {
        return levelRepository.getLevel(id)
    }
}