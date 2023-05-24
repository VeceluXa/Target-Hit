package com.danilovfa.targethit.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentBaseGameBinding
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.presentation.view.field.FieldView
import com.danilovfa.targethit.utils.Constants
import com.danilovfa.targethit.utils.TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

abstract class BaseGameFragment : Fragment() {
    protected val binding: FragmentBaseGameBinding by viewBinding(CreateMethod.INFLATE)

    protected abstract val scoreObserver: Observer<Int>
    protected abstract val timeObserver: Observer<Long>
    protected abstract var fieldView: FieldView
    protected abstract val targets: MutableList<Coordinate>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            setError(exception.message)
        }

        lifecycleScope.launch(coroutineExceptionHandler) {
            retrieveTargets()
            startGame()
        }
    }

    protected abstract suspend fun retrieveTargets()

    private fun startGame() {
        fieldView = getField()

        binding.apply {
            progressBar.visibility = View.GONE
            textScore.text = "0"
            textTime.text = "0.00"
            gameFieldLayout.addView(fieldView)
        }

        startTimer()
        startObservers()
    }

    private fun finishGame() {
        stopObservers()
        stopTimer()
        navigateToVictory()
    }

    protected abstract fun getField(): FieldView
    protected abstract fun startTimer()
    protected abstract fun stopTimer()
    protected abstract fun startObservers()
    protected abstract fun stopObservers()
    protected abstract fun updateViewModelScore()
    protected abstract fun navigateToVictory()

    protected fun checkTarget(milliseconds: Long) {
        if (fieldView.isTargetHit()) {
            Log.d(TAG, "startObservers: Hit")
            updateViewModelScore()
        }

        if (targets.size > 1 && milliseconds >= targets[0].t) {
            fieldView.updateTarget(targets[0])
            targets.removeAt(0)
        }

        if (targets.size == 1 && targets[0].x < 0 && targets[0].y < 0 && milliseconds >= targets[0].t) {
            fieldView.updateTarget(null)
            finishGame()
        }
    }

    private fun setError(message: String?) {
        binding.apply {
            progressBar.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            if (message != null && message != "")
                errorTextView.text = message
        }
    }

    protected  fun updateScore(score: Int) {
        binding.textScore.text = score.toString()
    }

    protected fun updateTime(milliseconds: Long) {
        val seconds = (milliseconds / 1000).toString()
        val fraction = (milliseconds % 1000 / Constants.STOPWATCH_UPDATE_TIME).toString().padStart(
            (1000 / Constants.STOPWATCH_UPDATE_TIME).toString().length - 1, '0')
        binding.textTime.text = getString(R.string.display_time, seconds, fraction)
    }
}