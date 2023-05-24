package com.danilovfa.targethit.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.usecase.GetImageResults
import com.danilovfa.targethit.domain.usecase.SetLevelCompletedByIdUseCase
import com.danilovfa.targethit.domain.usecase.SetScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VictoryViewModel @Inject constructor(
    private val setLevelCompletedByIdUseCase: SetLevelCompletedByIdUseCase,
    private val setScoreUseCase: SetScoreUseCase,
    private val getImageResults: GetImageResults
): ViewModel() {
    suspend fun setScore(score: Score) {
        setScoreUseCase.execute(score)
    }

    suspend fun setLevelCompleted(id: Int) {
        setLevelCompletedByIdUseCase.execute(id)
    }

    fun getImageResults(score: Score, imageWidth: Int, imageHeight: Int): Bitmap {
        return getImageResults.execute(score, imageWidth, imageHeight)
    }
}