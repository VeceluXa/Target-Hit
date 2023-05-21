package com.danilovfa.targethit.domain.repository

import com.danilovfa.targethit.domain.model.Score

interface LeaderboardRepository {
    suspend fun setScore(score: Score)
    suspend fun getLeaderboard(id: Int): List<Score>
    suspend fun getScore(date: String): Score
}