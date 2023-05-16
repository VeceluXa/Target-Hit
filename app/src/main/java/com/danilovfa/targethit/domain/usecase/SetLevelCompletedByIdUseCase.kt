package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.repository.LevelRepository
import javax.inject.Inject

class SetLevelCompletedByIdUseCase @Inject constructor(
    private val levelRepository: LevelRepository) {
    suspend fun execute(id: Int) {
        levelRepository.setComplete(id)
    }
}