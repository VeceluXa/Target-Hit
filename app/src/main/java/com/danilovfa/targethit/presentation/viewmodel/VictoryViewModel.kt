package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.usecase.SetLevelCompletedByIdUseCase
import com.danilovfa.targethit.domain.usecase.SetScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VictoryViewModel @Inject constructor(
    private val setLevelCompletedByIdUseCase: SetLevelCompletedByIdUseCase,
    private val setScoreUseCase: SetScoreUseCase
): ViewModel() {
    suspend fun setScore(score: Score) {
        setScoreUseCase.execute(score)
    }

    suspend fun setLevelCompleted(id: Int) {
        setLevelCompletedByIdUseCase.execute(id)
    }
}