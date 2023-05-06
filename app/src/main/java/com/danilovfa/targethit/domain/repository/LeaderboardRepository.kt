package com.danilovfa.targethit.domain.repository

import com.danilovfa.targethit.domain.model.Score

interface LeaderboardRepository {
    fun setScore(score: Score)
    suspend fun getLeaderboard(): List<Score>
}