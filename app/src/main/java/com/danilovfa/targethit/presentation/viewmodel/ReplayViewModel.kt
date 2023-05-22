package com.danilovfa.targethit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.usecase.GetLevelByIdUseCase
import com.danilovfa.targethit.domain.usecase.GetScoreByDateUseCase
import com.danilovfa.targethit.domain.usecase.StartStopwatchUseCase
import com.danilovfa.targethit.utils.Constants
import com.danilovfa.targethit.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReplayViewModel @Inject constructor(
    private val startStopwatchUseCase: StartStopwatchUseCase,
    private val getScoreByDateUseCase: GetScoreByDateUseCase,
    private val getLevelByIdUseCase: GetLevelByIdUseCase
): ViewModel() {
    var crosshairMovements: MutableList<Coordinate>? = null
    var level: Level? = null

    private val _milliseconds = MutableLiveData(0L)
    val milliseconds: LiveData<Long> get() = _milliseconds

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val timeObserver = Observer<Long> {
        _milliseconds.value = it
    }

    suspend fun getScore(date: String) = getScoreByDateUseCase.execute(date)
    suspend fun getLevel(id: Int) = getLevelByIdUseCase.execute(id)

    fun updateScore() {
        _score.value = score.value!! + Constants.SCORE_UPDATE_VALUE
    }

    fun finishGame() {
        startStopwatchUseCase.execute().removeObserver(timeObserver)
    }

    fun startTimer() {
        startStopwatchUseCase.execute().observeForever(timeObserver)
    }

    fun getCrosshair(): Coordinate? {
        if (crosshairMovements == null)
            return null

        if (crosshairMovements!!.isEmpty())
            return null

        val lastItem = crosshairMovements!!.lastOrNull { it.t < milliseconds.value!! }

        if (lastItem != null) {
            crosshairMovements!!.removeAll { it.t <= lastItem.t }
            Log.d(TAG, "getCrosshair: $lastItem")
            return lastItem
        }

        return null
    }
}