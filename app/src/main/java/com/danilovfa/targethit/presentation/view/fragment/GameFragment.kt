package com.danilovfa.targethit.presentation.view.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.presentation.mapper.CoordinateArgsMapper
import com.danilovfa.targethit.presentation.model.LevelDestinations
import com.danilovfa.targethit.presentation.model.ScoreArgs
import com.danilovfa.targethit.presentation.view.field.FieldView
import com.danilovfa.targethit.presentation.view.field.GameFieldView
import com.danilovfa.targethit.presentation.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@AndroidEntryPoint
class GameFragment : BaseGameFragment() {

    private val viewModel: GameViewModel by viewModels()
    private val args: GameFragmentArgs by navArgs()
    override lateinit var fieldView: FieldView
    override var targets = mutableListOf<Coordinate>()

    override val scoreObserver = Observer<Int> { score ->
        updateScore(score)
    }

    override val timeObserver = Observer<Long> { milliseconds ->
        updateTime(milliseconds)
        updateLog(milliseconds)
        checkTarget(milliseconds)
    }

    override suspend fun retrieveTargets() {
        if (args.targetsCustom != null) {
            val argsMapper = CoordinateArgsMapper()
            targets = args.targetsCustom!!.map { coordinateArgs ->
                argsMapper.fromEntity(coordinateArgs)
            }.toMutableList()
            return
        }

        var level: Level?
        withContext(Dispatchers.IO) {
            level = viewModel.getLevel(args.level)
        }

        if (level == null)
            throw Exception("Error occurred while retrieving level data.")

        viewModel.level = level
        targets += level!!.targets
    }

    override fun getField(): FieldView {
        return GameFieldView(requireContext())
    }

    override fun startTimer() {
        viewModel.startTimer()
    }

    override fun stopTimer() {
        viewModel.stopTimer()
    }

    override fun navigateToVictory() {
        val coordinateArgsMapper = CoordinateArgsMapper()
        val scoreArgs = ScoreArgs(
            levelId = args.level,
            score = viewModel.score.value ?: 0,
            date = LocalDateTime.now().toString(),
            log = viewModel.gameLog.map { coordinate ->
                coordinateArgsMapper.fromDomain(coordinate)
            }
        )

        val action = GameFragmentDirections.actionGameFragmentToVictoryFragment(
            score = scoreArgs,
            isCustom = args.targetsCustom != null,
            destination = LevelDestinations.GAME,
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

    private fun updateLog(milliseconds: Long) {
        if (fieldView.width == 0 || fieldView.height == 0) return

        val coordinate = Coordinate(
            x = fieldView.crosshairX,
            y = fieldView.crosshairY,
            t = milliseconds.toInt()
        )
        viewModel.updateLog(coordinate)
    }
}