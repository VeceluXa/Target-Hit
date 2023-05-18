package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentCustomLevelBinding
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.presentation.mapper.CoordinateArgsMapper
import com.danilovfa.targethit.presentation.viewmodel.CustomLevelViewModel
import com.danilovfa.targethit.utils.toast


class CustomLevelFragment : Fragment() {

    val binding: FragmentCustomLevelBinding by viewBinding(CreateMethod.INFLATE)
    val viewModel: CustomLevelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCustomStart.setOnClickListener { onStartClick() }
    }

    private fun onStartClick() {
        if (areTargetsCorrect())
            navigateToGame()
    }

    private fun areTargetsCorrect(): Boolean {
        if (viewModel.targets.size == 0) {
            requireContext().toast(resources.getString(R.string.custom_level_error_no_targets))
            return false
        }
        if (binding.editTextEndTime.text.toString().toIntOrNull() == null) {
            requireContext().toast(resources.getString(R.string.custom_level_error_no_end_time))
            return false
        }
        if (binding.editTextEndTime.text.toString().toInt() < viewModel.targets.last().t) {
            requireContext().toast(resources.getString(R.string.custom_level_error_end_time_small))
            return false
        }
        return true
    }

    private fun navigateToGame() {
        viewModel.targets.add(Coordinate(
            x = -1,
            y = -1,
            t = binding.editTextEndTime.text.toString().toInt()
        ))

        val argsMapper = CoordinateArgsMapper()
        val action = CustomLevelFragmentDirections.actionCustomLevelFragmentToGameFragment(
            targetsCustom = viewModel.targets.map { coordinate ->
                argsMapper.fromDomain(coordinate)
            }.toTypedArray()
        )
        findNavController().navigate(action)
    }
}