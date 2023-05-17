package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import javax.inject.Inject

class GetLeaderboardUseCase @Inject constructor(
    private val leaderboardRepository: LeaderboardRepository) {
    suspend fun execute(id: Int): List<Score> {
        return leaderboardRepository.getLeaderboard(id)
    }
}