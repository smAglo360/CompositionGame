package com.smaglo360.compositiongame.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.smaglo360.compositiongame.R
import com.smaglo360.compositiongame.databinding.FragmentLevelOptionsBinding
import com.smaglo360.compositiongame.domain.enteties.Level

class LevelOptionsFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            mbTestLevel.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            mbEasyLevel.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            mbMediumLevel.setOnClickListener {
                launchGameFragment(Level.MEDIUM)
            }
            mbHardLevel.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            LevelOptionsFragmentDirections.actionLevelOptionsFragmentToGameFragment(level
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}