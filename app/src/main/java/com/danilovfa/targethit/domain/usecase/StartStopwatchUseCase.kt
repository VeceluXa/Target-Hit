package com.danilovfa.targethit.domain.usecase

import androidx.lifecycle.LiveData
import com.danilovfa.targethit.domain.time.StopwatchListOrchestrator
import javax.inject.Inject

class StartStopwatchUseCase @Inject constructor(
    private val stopwatchListOrchestrator: StopwatchListOrchestrator
) {
    fun execute(): LiveData<Long> {
        stopwatchListOrchestrator.start()
        return stopwatchListOrchestrator.milliseconds
    }
}