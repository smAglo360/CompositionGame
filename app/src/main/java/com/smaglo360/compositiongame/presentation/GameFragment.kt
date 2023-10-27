package com.smaglo360.compositiongame.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.smaglo360.compositiongame.R
import com.smaglo360.compositiongame.databinding.FragmentGameBinding
import com.smaglo360.compositiongame.domain.enteties.GameResult
import com.smaglo360.compositiongame.domain.enteties.GameSettings
import com.smaglo360.compositiongame.domain.enteties.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private lateinit var viewModel: GameViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameResultFragment.newInstance(gameResult))
            .addToBackStack(NAME)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {

        const val NAME = "GameFragment"

        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}