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
import com.danilovfa.targethit.databinding.FragmentGameBinding
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.presentation.mapper.CoordinateArgsMapper
import com.danilovfa.targethit.presentation.model.LevelDestinations
import com.danilovfa.targethit.presentation.model.ScoreArgs
import com.danilovfa.targethit.presentation.view.field.GameFieldView
import com.danilovfa.targethit.presentation.viewmodel.GameViewModel
import com.danilovfa.targethit.utils.Constants.Companion.STOPWATCH_UPDATE_TIME
import com.danilovfa.targethit.utils.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@AndroidEntryPoint
class GameFragment : Fragment() {

    private val binding: FragmentGameBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: GameViewModel by viewModels()
    private val args: GameFragmentArgs by navArgs()
    private lateinit var gameFieldView: GameFieldView
    private var targets = mutableListOf<Coordinate>()

    private val scoreObserver = Observer<Int> { score ->
        updateScore(score)
    }

    private val timeObserver = Observer<Long> { milliseconds ->
        updateTime(milliseconds)
        updateLog(milliseconds)
        checkTarget(milliseconds)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.targetsCustom != null) {
            val argsMapper = CoordinateArgsMapper()
            targets = args.targetsCustom!!.map {  coordinateArgs ->
                argsMapper.fromEntity(coordinateArgs)
            }.toMutableList()
            startGame()
        } else
            retrieveTargets()

    }

    private fun retrieveTargets() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            setError(exception.message)
        }

        lifecycleScope.launch(coroutineExceptionHandler) {
            var level: Level?
            withContext(Dispatchers.IO) {
                level = viewModel.getLevel(args.level)
            }

            if (level != null) {
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
        gameFieldView = GameFieldView(requireContext())

        binding.apply {
            progressBar.visibility = View.GONE
            textScore.text = "0"
            textTime.text = "0.00"
            gameFieldLayout.addView(gameFieldView)
        }

        viewModel.startTimer()
        startObservers()
    }

    private fun finishGame() {
        stopObservers()
        viewModel.finishGame()

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
            destination = LevelDestinations.GAME
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

    private fun updateLog(milliseconds: Long) {
        if (gameFieldView.width == 0 || gameFieldView.height == 0) return

        val coordinate = Coordinate(
            x = gameFieldView.crosshairX,
            y = gameFieldView.crosshairY,
            t = milliseconds.toInt()
        )
        viewModel.updateLog(coordinate)
    }

    private fun checkTarget(milliseconds: Long) {
        if (gameFieldView.isTargetHit()) {
            Log.d(TAG, "startObservers: Hit")
            viewModel.updateScore()
        }

        if (targets.size > 1 && milliseconds >= targets[0].t) {
            gameFieldView.updateTarget(targets[0])
            targets.removeAt(0)
        }

        if (targets.size == 1 && targets[0].x < 0 && targets[0].y < 0 && milliseconds >= targets[0].t) {
            gameFieldView.updateTarget(null)
            finishGame()
        }
    }

    private fun updateTime(milliseconds: Long) {
        val seconds = (milliseconds / 1000).toString()
        val fraction = (milliseconds % 1000 / STOPWATCH_UPDATE_TIME).toString().padStart(
            (1000 / STOPWATCH_UPDATE_TIME).toString().length - 1, '0')
        binding.textTime.text = getString(R.string.display_time, seconds, fraction)
    }
}