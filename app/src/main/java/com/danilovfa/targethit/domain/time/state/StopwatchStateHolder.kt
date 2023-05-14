package com.danilovfa.targethit.domain.time.state

import com.danilovfa.targethit.domain.time.timestamp.ElapsedTimeCalculator
import javax.inject.Inject

class StopwatchStateHolder @Inject constructor(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator
) {
    var currentState: StopwatchState = StopwatchState.Paused(0)
        private set

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    fun getMilliseconds(): Long = when (val currentState = currentState) {
        is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        is StopwatchState.Paused -> currentState.elapsedTime
    }
}