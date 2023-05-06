package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.usecase.GetLevelByIdUseCase
import com.danilovfa.targethit.domain.usecase.SetLevelCompletedByIdUseCase
import com.danilovfa.targethit.domain.usecase.SetScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getLevelByIdUseCase: GetLevelByIdUseCase,
    private val setScoreUseCase: SetScoreUseCase,
    private val setLevelCompletedByIdUseCase: SetLevelCompletedByIdUseCase
): ViewModel() {
}