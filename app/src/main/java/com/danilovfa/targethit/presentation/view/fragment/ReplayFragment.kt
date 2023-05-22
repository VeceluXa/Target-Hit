package com.danilovfa.targethit.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentReplayBinding
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.presentation.mapper.CoordinateMapper
import com.danilovfa.targethit.presentation.model.LevelDestinations
import com.danilovfa.targethit.presentation.model.ScoreArgs
import com.danilovfa.targethit.presentation.view.field.ReplayFieldView
import com.danilovfa.targethit.presentation.viewmodel.ReplayViewModel
import com.danilovfa.targethit.utils.Constants
import com.danilovfa.targethit.utils.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@AndroidEntryPoint
class ReplayFragment : Fragment() {

    private val binding: FragmentReplayBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: ReplayViewModel by viewModels()
    private val args: ReplayFragmentArgs by navArgs()
    private lateinit var replayFieldView: ReplayFieldView
    private var targets = mutableListOf<Coordinate>()

    private val scoreObserver = Observer<Int> { score ->
        updateScore(score)
    }

    private val timeObserver = Observer<Long> { milliseconds ->
        updateTime(milliseconds)
        updateCrosshair()
        checkTarget(milliseconds)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveScore()
    }

    private fun retrieveScore() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            setError(exception.message)
        }

        lifecycleScope.launch() {
            var score: Score?
            var level: Level?
            withContext(Dispatchers.IO) {
                score = viewModel.getScore(args.scoreDate)
                level = viewModel.getLevel(args.levelId)
            }

            if (score != null && level != null) {
                viewModel.crosshairMovements = score!!.log.toMutableList()
                Log.d(TAG, "retrieveScore: ${viewModel.crosshairMovements}")
                viewModel.level = level
                targets += level!!.targets

                startGame()
            } else
                throw Exception("Error occurred while retrieving level data.")
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

    private fun startGame() {
        replayFieldView = ReplayFieldView(requireContext())

        startObservers()
        viewModel.startTimer()

        binding.apply {
            progressBar.visibility = View.GONE
            textScore.text = "0"
            textTime.text = "0.00"
            gameFieldLayout.addView(replayFieldView)
        }
    }

    private fun finishGame() {
        stopObservers()
        viewModel.finishGame()

        val scoreArgs = ScoreArgs(
            levelId = args.levelId,
            score = viewModel.score.value ?: 0,
            date = LocalDateTime.now().toString(),
            log = emptyList()
        )

        val action = ReplayFragmentDirections.actionReplayFragmentToVictoryFragment(
            score = scoreArgs,
            isCustom = true,
            destination = LevelDestinations.LEADERBOARD
        )

        findNavController().navigate(action)
    }

    private fun startObservers() {
        viewModel.score.observe(viewLifecycleOwner, scoreObserver)
        viewModel.milliseconds.observe(viewLifecycleOwner, timeObserver)
    }

    private fun stopObservers() {
        viewModel.score.removeObserver(scoreObserver)
        viewModel.milliseconds.removeObserver(timeObserver)
    }

    private fun updateScore(score: Int) {
        binding.textScore.text = score.toString()
    }

    private fun updateCrosshair() {
        val crosshair = viewModel.getCrosshair() ?: return
        replayFieldView.updateCrosshair(crosshair)
    }

    private fun checkTarget(milliseconds: Long) {
        if (replayFieldView.isTargetHit()) {
            Log.d(TAG, "startObservers: Hit")
            viewModel.updateScore()
        }

        if (targets.size > 1 && milliseconds >= targets[0].t) {
            replayFieldView.updateTarget(targets[0])
            targets.removeAt(0)
        }

        if (targets.size == 1 && targets[0].x < 0 && targets[0].y < 0 && milliseconds >= targets[0].t) {
            replayFieldView.updateTarget(null)
            finishGame()
        }
    }

    private fun updateTime(milliseconds: Long) {
        val seconds = (milliseconds / 1000).toString()
        val fraction = (milliseconds % 1000 / Constants.STOPWATCH_UPDATE_TIME).toString().padStart(
            (1000 / Constants.STOPWATCH_UPDATE_TIME).toString().length - 1, '0')
        binding.textTime.text = getString(R.string.display_time, seconds, fraction)
    }
}