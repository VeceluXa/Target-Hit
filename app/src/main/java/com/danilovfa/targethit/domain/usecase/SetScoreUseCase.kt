package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import javax.inject.Inject

class SetScoreUseCase @Inject constructor(
    private val leaderboardRepository: LeaderboardRepository) {
    suspend fun execute(score: Score) {
        leaderboardRepository.setScore(score)
    }
}