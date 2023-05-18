package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.model.Coordinate
import dagger.hilt.android.lifecycle.HiltViewModel

class CustomLevelViewModel: ViewModel() {
    val targets = mutableListOf<Coordinate>()
}