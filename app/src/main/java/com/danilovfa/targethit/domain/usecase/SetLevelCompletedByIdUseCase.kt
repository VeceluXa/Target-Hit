package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.repository.LevelRepository

class SetLevelCompletedById(private val levelRepository: LevelRepository) {
    fun execute(id: Int) {
        levelRepository.setComplete(id)
    }
}