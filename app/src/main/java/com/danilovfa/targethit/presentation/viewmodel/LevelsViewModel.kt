package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.usecase.GetLevelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor(
    private val getLevelsUseCase: GetLevelsUseCase
): ViewModel() {
}