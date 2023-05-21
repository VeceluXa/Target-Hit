package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.databinding.FragmentReplayBinding
import com.danilovfa.targethit.presentation.viewmodel.ReplayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReplayFragment : Fragment() {

    private val binding: FragmentReplayBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: ReplayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }
}