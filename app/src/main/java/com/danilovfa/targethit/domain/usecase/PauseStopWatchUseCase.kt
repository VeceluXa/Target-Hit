package com.danilovfa.targethit.domain.usecase

import com.danilovfa.targethit.domain.time.StopwatchListOrchestrator
import javax.inject.Inject

class PauseStopWatchUseCase @Inject constructor(
    private val stopwatchListOrchestrator: StopwatchListOrchestrator
) {
    fun execute() {
        stopwatchListOrchestrator.pause()
    }
}