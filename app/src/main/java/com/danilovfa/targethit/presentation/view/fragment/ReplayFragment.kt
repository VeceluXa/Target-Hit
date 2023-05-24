package com.danilovfa.targethit.presentation.view.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.presentation.mapper.CoordinateArgsMapper
import com.danilovfa.targethit.presentation.model.LevelDestinations
import com.danilovfa.targethit.presentation.model.ScoreArgs
import com.danilovfa.targethit.presentation.view.field.FieldView
import com.danilovfa.targethit.presentation.view.field.ReplayFieldView
import com.danilovfa.targethit.presentation.viewmodel.ReplayViewModel
import com.danilovfa.targethit.utils.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@AndroidEntryPoint
class ReplayFragment : BaseGameFragment() {

    private val viewModel: ReplayViewModel by viewModels()
    private val args: ReplayFragmentArgs by navArgs()
    override lateinit var fieldView: FieldView
    override var targets = mutableListOf<Coordinate>()
    private lateinit var scoreData: Score

    override val scoreObserver = Observer<Int> { score ->
        updateScore(score)
    }

    override val timeObserver = Observer<Long> { milliseconds ->
        updateTime(milliseconds)
        updateCrosshair()
        checkTarget(milliseconds)
    }

    override suspend fun retrieveTargets() {
        var score: Score?
        var level: Level?
        withContext(Dispatchers.IO) {
            score = viewModel.getScore(args.scoreDate)
            level = viewModel.getLevel(args.levelId)
        }

        if (score == null || level == null)
            throw Exception("Error occurred while retrieving level data.")

        scoreData = score!!
        viewModel.crosshairMovements = score!!.log.toMutableList()
        Log.d(TAG, "retrieveScore: ${viewModel.crosshairMovements}")
        viewModel.level = level
        targets += level!!.targets
    }

    override fun getField(): FieldView {
        return ReplayFieldView(requireContext())
    }

    override fun startTimer() {
        viewModel.startTimer()
    }

    override fun stopTimer() {
        viewModel.stopTimer()
    }

    override fun navigateToVictory() {
        val coordinateMapper = CoordinateArgsMapper()

        val scoreArgs = ScoreArgs(
            levelId = args.levelId,
            score = scoreData.score,
            date = LocalDateTime.now().toString(),
            log = scoreData.log.map { coordinate ->
                coordinateMapper.fromDomain(coordinate)
            }
        )

        val action = ReplayFragmentDirections.actionReplayFragmentToVictoryFragment(
            score = scoreArgs,
            isCustom = true,
            destination = LevelDestinations.LEADERBOARD,
            fieldWidth = fieldView.measuredWidth,
            fieldHeight = fieldView.measuredHeight
        )

        findNavController().navigate(action)
    }

    override fun startObservers() {
        viewModel.score.observe(viewLifecycleOwner, scoreObserver)
        viewModel.milliseconds.observe(viewLifecycleOwner, timeObserver)
    }

    override fun stopObservers() {
        viewModel.score.removeObserver(scoreObserver)
        viewModel.milliseconds.removeObserver(timeObserver)
    }

    override fun updateViewModelScore() {
        viewModel.updateScore()
    }

    private fun updateCrosshair() {
        val crosshair = viewModel.getCrosshair() ?: return
        (fieldView as ReplayFieldView).updateCrosshair(crosshair)
    }
}