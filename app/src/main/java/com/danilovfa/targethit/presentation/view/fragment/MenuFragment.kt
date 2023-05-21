package com.danilovfa.targethit.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.databinding.FragmentMenuBinding
import com.danilovfa.targethit.presentation.model.LevelDestinations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val binding: FragmentMenuBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonStart.setOnClickListener { onButtonStart() }
            buttonLeaderboard.setOnClickListener { onButtonLeaderboard() }
            buttonSettings.setOnClickListener { onButtonSettings() }
            buttonExit.setOnClickListener { onButtonExit() }
        }
    }

    private fun onButtonStart() {
        val action =
            MenuFragmentDirections.actionMenuFragmentToLevelsFragment(LevelDestinations.GAME)
        findNavController().navigate(action)
    }

    private fun onButtonLeaderboard() {
        val action =
            MenuFragmentDirections.actionMenuFragmentToLevelsFragment(LevelDestinations.LEADERBOARD)
        findNavController().navigate(action)
    }

    private fun onButtonSettings() {
        val action = MenuFragmentDirections.actionMenuFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun onButtonExit() {
        requireActivity().finishAffinity()
    }
}