package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.usecase.GetScoreByDateUseCase
import com.danilovfa.targethit.domain.usecase.StartStopwatchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReplayViewModel @Inject constructor(
    private val startStopwatchUseCase: StartStopwatchUseCase,
    private val getScoreByIdUseCase: GetScoreByDateUseCase
): ViewModel() {
}