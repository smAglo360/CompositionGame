package com.smaglo360.compositiongame.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smaglo360.compositiongame.R
import com.smaglo360.compositiongame.databinding.FragmentLevelOptionsBinding
import com.smaglo360.compositiongame.databinding.FragmentWelcomeBinding

class LevelOptions : Fragment() {

    private var _binding: FragmentLevelOptionsBinding? = null
    private val binding: FragmentLevelOptionsBinding
        get() = _binding ?: throw RuntimeException("FragmentLevelOptionsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLevelOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}