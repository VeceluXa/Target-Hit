package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import javax.inject.Inject

class GetScoreByDateUseCase @Inject constructor(
    private val leaderboardRepository: LeaderboardRepository
) {
    suspend fun execute(date: String): Score {
        return leaderboardRepository.getScore(date)
    }
}