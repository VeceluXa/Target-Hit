package com.danilovfa.targethit.domain.time.timestamp

import com.danilovfa.targethit.domain.time.state.StopwatchState
import javax.inject.Inject

class ElapsedTimeCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider
) {
    fun calculate(state: StopwatchState.Running): Long {
        val currentTimeStamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimeStamp > state.startTime) {
            currentTimeStamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart
    }
}