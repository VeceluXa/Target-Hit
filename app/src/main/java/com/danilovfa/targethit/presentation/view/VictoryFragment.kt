package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentVictoryBinding
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.presentation.mapper.ScoreArgsMapper
import com.danilovfa.targethit.presentation.model.LevelDestinations
import com.danilovfa.targethit.presentation.viewmodel.VictoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class VictoryFragment : Fragment() {

    private val binding: FragmentVictoryBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: VictoryViewModel by viewModels()
    private val args: VictoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val score = ScoreArgsMapper().fromEntity(args.score)

        binding.apply {
            textVictoryScore.text = resources.getString(R.string.victory_score, score.score)
            buttonVictory.setOnClickListener {
                if (!args.isCustom)
                    saveScore(score)
                else
                    navigateToLevels()
            }
        }
    }

    private fun saveScore(score: Score) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            val message = exception.message ?: resources.getString(R.string.victory_error_default)
            setError(message)
        }
        lifecycleScope.launch(coroutineExceptionHandler) {
            setLoader()
            withContext(Dispatchers.IO) {
                viewModel.setScore(score)
                viewModel.setLevelCompleted(score.levelId)
            }
            navigateToLevels()
        }
    }

    private fun setError(message: String) {
        binding.apply {
            victoryLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
        }
    }

    private fun setLoader() {
        binding.apply {
            victoryLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun navigateToLevels() {
        val action = VictoryFragmentDirections.actionVictoryFragmentToLevelsFragment(LevelDestinations.GAME)
        findNavController().navigate(action)
    }
}