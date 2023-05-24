package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.*
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.usecase.*
import com.danilovfa.targethit.utils.Constants.Companion.SCORE_UPDATE_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getLevelByIdUseCase: GetLevelByIdUseCase,
    private val startStopwatchUseCase: StartStopwatchUseCase,
    private val pauseStopWatchUseCase: PauseStopWatchUseCase
): ViewModel() {
    private var _gameLog = mutableListOf<Coordinate>()
    val gameLog get() = _gameLog.toList()

    private val _milliseconds = MutableLiveData(0L)
    val milliseconds: LiveData<Long> get() = _milliseconds

    private val _score = MutableLiveData(0)
    val score get() = _score

    var level: Level? = null

    private val timeObserver = Observer<Long> {
        _milliseconds.value = it
    }
    suspend fun getLevel(id: Int): Level {
        return getLevelByIdUseCase.execute(id)
    }

    fun stopTimer() {
        startStopwatchUseCase.execute().removeObserver(timeObserver)
        pauseStopWatchUseCase.execute()
    }

    fun startTimer() {
        startStopwatchUseCase.execute().observeForever(timeObserver)
    }

    fun updateLog(coordinate: Coordinate) {
        _gameLog.add(coordinate)
    }

    fun updateScore() {
        score.value = score.value!! + SCORE_UPDATE_VALUE
    }
}