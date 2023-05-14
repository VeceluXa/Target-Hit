package com.danilovfa.targethit.domain.time.state

import com.danilovfa.targethit.domain.time.timestamp.ElapsedTimeCalculator
import com.danilovfa.targethit.domain.time.timestamp.TimestampProvider
import javax.inject.Inject

class StopwatchStateCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator
) {
    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running = when(oldState) {
        is StopwatchState.Running -> oldState
        is StopwatchState.Paused -> {
            StopwatchState.Running(
                startTime = timestampProvider.getMilliseconds(),
                elapsedTime = oldState.elapsedTime
            )
        }
    }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused = when(oldState) {
        is StopwatchState.Running -> {
            val elapsedTime = elapsedTimeCalculator.calculate(oldState)
            StopwatchState.Paused(elapsedTime)
        }
        is StopwatchState.Paused -> oldState
    }
}