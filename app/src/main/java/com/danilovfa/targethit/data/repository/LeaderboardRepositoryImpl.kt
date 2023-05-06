package com.danilovfa.targethit.data.repository

import com.danilovfa.targethit.data.local.dao.LeaderboardDao
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import javax.inject.Inject

class LeaderboardRepositoryImpl @Inject constructor(
    leaderboardDao: LeaderboardDao): LeaderboardRepository {
    override fun setScore(score: Score) {
        TODO("Not yet implemented")
    }

    override suspend fun getLeaderboard(): List<Score> {
        TODO("Not yet implemented")
    }
}