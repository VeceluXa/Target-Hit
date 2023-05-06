package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.repository.LeaderboardRepository

class GetLeaderboard(private val leaderboardRepository: LeaderboardRepository) {
    suspend fun execute(): List<Score> {
        return leaderboardRepository.getLeaderboard()
    }
}