package com.danilovfa.targethit.presentation.viewmodel

import androidx.lifecycle.*
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.domain.usecase.*
import com.danilovfa.targethit.utils.Constants.Companion.SCORE_UPDATE_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getLevelByIdUseCase: GetLevelByIdUseCase,
    private val setScoreUseCase: SetScoreUseCase,
    private val setLevelCompletedByIdUseCase: SetLevelCompletedByIdUseCase,
    private val startStopwatchUseCase: StartStopwatchUseCase,
    private val pauseStopWatchUseCase: PauseStopWatchUseCase
): ViewModel() {
    private val gameLog = mutableListOf<Coordinate>()

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

    fun finishGame() {
        stopTimer()
        if (level != null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    setLevelCompletedByIdUseCase.execute(level!!.id)
                    setScoreUseCase.execute(Score(
                        levelId = level!!.id,
                        score = score.value!!,
                        date = LocalDateTime.now(),
                        log = gameLog
                    ))
                }
            }
        }

    }

    fun startTimer() {
        startStopwatchUseCase.execute().observeForever(timeObserver)
    }

    private fun stopTimer() {
        startStopwatchUseCase.execute().removeObserver(timeObserver)
        pauseStopWatchUseCase.execute()
    }

    fun updateLog(coordinate: Coordinate) {
        gameLog.add(coordinate)
    }

    fun updateScore() {
        score.value = score.value!! + SCORE_UPDATE_VALUE
    }
}