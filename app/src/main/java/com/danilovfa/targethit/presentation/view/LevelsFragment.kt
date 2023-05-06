package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.databinding.FragmentLevelsBinding
import com.danilovfa.targethit.presentation.viewmodel.LevelsViewModel

class LevelsFragment : Fragment() {

    private val binding: FragmentLevelsBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: LevelsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}