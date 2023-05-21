package com.danilovfa.targethit.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.databinding.FragmentLevelsBinding
import com.danilovfa.targethit.domain.model.LevelItem
import com.danilovfa.targethit.presentation.adapter.LevelsAdapter
import com.danilovfa.targethit.presentation.model.LevelDestinations
import com.danilovfa.targethit.presentation.viewmodel.LevelsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LevelsFragment : Fragment(), LevelsAdapter.OnItemClickListener {

    private val binding: FragmentLevelsBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: LevelsViewModel by viewModels()
    private val args: LevelsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.destination == LevelDestinations.GAME) {
            binding.buttonLevelCustom.apply {
                visibility = View.VISIBLE
                setOnClickListener { navigateToCustom() }
            }
        }

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            setError(exception.message)
        }

        lifecycleScope.launch(coroutineExceptionHandler) {
            val levels = mutableListOf<LevelItem>()
            withContext(Dispatchers.IO) {
                levels += viewModel.getLevels()
            }
            drawLevels(levels)
        }
    }

    private fun drawLevels(levels: List<LevelItem>) {
        setVisibility()
        setAdapter(levels)
    }

    private fun setVisibility() {
        binding.apply {
            levelsLayout.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    private fun setAdapter(levels: List<LevelItem>) {
        val myAdapter = LevelsAdapter(requireContext(), levels)
        myAdapter.setOnItemClickListener(this)
        binding.levelsRecyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext())
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

    override fun onItemClick(id: Int) {
        navigate(id)
    }

    private fun navigate(id: Int) {
        val action = if (args.destination == LevelDestinations.GAME)
            LevelsFragmentDirections.actionLevelsFragmentToGameFragment(level = id)
        else
            LevelsFragmentDirections.actionLevelsFragmentToLeaderboardFragment(level = id)

        findNavController().navigate(action)
    }

    private fun navigateToCustom() {
        val action = LevelsFragmentDirections.actionLevelsFragmentToCustomLevelFragment()
        findNavController().navigate(action)
    }
}