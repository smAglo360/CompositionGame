package com.smaglo360.compositiongame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.smaglo360.compositiongame.R
import com.smaglo360.compositiongame.databinding.FragmentGameResultBinding
import com.smaglo360.compositiongame.domain.enteties.GameResult

class GameResultFragment : Fragment() {


    private val args by navArgs<GameResultFragmentArgs>()

    private var _binding: FragmentGameResultBinding? = null

    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCallbacks()
        setOnClickListeners()
        bindViews()
    }

    private fun bindViews() {
        binding.gameResult = args.gameResult
    }

    private fun setOnClickListeners() {
        binding.mbStartAgain.setOnClickListener {
            retryGame()
        }
    }

    private fun setCallbacks() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}