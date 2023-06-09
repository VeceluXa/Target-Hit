package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.usecase.GetLeaderboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getLeaderboardUseCase: GetLeaderboardUseCase
): ViewModel() {
    suspend fun getLeaderboard(id: Int): List<Score> {
        return getLeaderboardUseCase.execute(id)
    }
}