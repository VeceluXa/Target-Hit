package com.danilovfa.targethit.data.repository

import com.danilovfa.targethit.data.local.dao.LeaderboardDao
import com.danilovfa.targethit.data.mapper.ScoreEntityMapper
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.repository.LeaderboardRepository
import javax.inject.Inject

class LeaderboardRepositoryImpl @Inject constructor(
    private val leaderboardDao: LeaderboardDao): LeaderboardRepository {
    val scoreEntityMapper = ScoreEntityMapper()
    override suspend fun setScore(score: Score) {
        leaderboardDao.setScore(scoreEntityMapper.fromDomain(score))
    }

    override suspend fun getLeaderboard(): List<Score> {
        return leaderboardDao.getTopScores().map { scoreEntity ->
            scoreEntityMapper.fromEntity(scoreEntity)
        }
    }
}