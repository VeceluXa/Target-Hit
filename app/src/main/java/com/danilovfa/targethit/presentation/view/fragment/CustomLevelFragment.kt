package com.danilovfa.targethit.presentation.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentCustomLevelBinding
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.presentation.adapter.CustomLevelAdapter
import com.danilovfa.targethit.presentation.mapper.CoordinateArgsMapper
import com.danilovfa.targethit.presentation.view.CustomLevelFragmentDirections
import com.danilovfa.targethit.presentation.viewmodel.CustomLevelViewModel
import com.danilovfa.targethit.utils.Constants.Companion.DOMAIN_GAME_FIELD_HEIGHT
import com.danilovfa.targethit.utils.Constants.Companion.DOMAIN_GAME_FIELD_WIDTH
import com.danilovfa.targethit.utils.TAG
import com.danilovfa.targethit.utils.toast


class CustomLevelFragment : Fragment() {

    val binding: FragmentCustomLevelBinding by viewBinding(CreateMethod.INFLATE)
    val viewModel: CustomLevelViewModel by viewModels()
    private lateinit var listAdapter: CustomLevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.apply {
            buttonCustomStart.setOnClickListener {
                if (areTargetsCorrect())
                    navigateToGame()
            }
            buttonAddLevel.setOnClickListener { onAddItem() }
        }
    }

    private fun setupRecyclerView() {
        listAdapter = CustomLevelAdapter(requireContext(), viewModel.targets)
        binding.customLevelRecyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onAddItem() {
        with(viewModel.targets) {
            if (size > 0 && (last().t == -1 ||
                last().x !in 0..DOMAIN_GAME_FIELD_WIDTH ||
                last().y !in 0..DOMAIN_GAME_FIELD_HEIGHT)) {
                requireContext().toast(resources.getString(R.string.custom_level_error_wrong_coordinate))
                return
            }

            if (size > 1 && last().t <= get(lastIndex - 1).t) {
                requireContext().toast(resources.getString(R.string.custom_level_error_time_small))
                return
            }
        }

        viewModel.targets.add(Coordinate(-1, -1, -1))
        listAdapter.notifyDataSetChanged()
        Log.d(TAG, "onAddItem: ${viewModel.targets}")
        binding.customLevelRecyclerView.scrollToPosition(viewModel.targets.lastIndex + 1)
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
            requireContext().toast(resources.getString(R.string.custom_level_error_time_small))
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