package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private val binding: FragmentMenuBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}