package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.databinding.FragmentLevelsBinding
import com.danilovfa.targethit.presentation.model.LevelDestinations
import com.danilovfa.targethit.presentation.viewmodel.LevelsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelsFragment : Fragment() {

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
    }

    private fun navigate(id: Int) {
        val action = if (args.destination == LevelDestinations.GAME)
            LevelsFragmentDirections.actionLevelsFragmentToGameFragment(id)
        else
            LevelsFragmentDirections.actionLevelsFragmentToLeaderboardFragment(id)

        findNavController().navigate(action)
    }
}