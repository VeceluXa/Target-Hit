package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentVictoryBinding
import com.danilovfa.targethit.presentation.mapper.ScoreArgsMapper
import com.danilovfa.targethit.presentation.viewmodel.VictoryViewModel
import com.danilovfa.targethit.utils.TAG

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
        Log.d(TAG, "onViewCreated: $score")
    }
}