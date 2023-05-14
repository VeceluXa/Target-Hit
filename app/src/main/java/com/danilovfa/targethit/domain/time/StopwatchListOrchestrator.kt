package com.danilovfa.targethit.domain.time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danilovfa.targethit.domain.time.state.StopwatchStateHolder
import com.danilovfa.targethit.utils.Constants.Companion.STOPWATCH_UPDATE_TIME
import kotlinx.coroutines.*
import javax.inject.Inject

class StopwatchListOrchestrator @Inject constructor(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
) {
    private var job: Job? = null

    private val _milliseconds = MutableLiveData(0L)
    val milliseconds: LiveData<Long> get() = _milliseconds

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        scope.launch {
            while(isActive) {
                _milliseconds.postValue(stopwatchStateHolder.getMilliseconds())
                delay(STOPWATCH_UPDATE_TIME.toLong())
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearTime()
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearTime() {
        _milliseconds.value = 0
    }



}