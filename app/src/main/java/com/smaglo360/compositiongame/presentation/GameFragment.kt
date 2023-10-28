package com.smaglo360.compositiongame.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.smaglo360.compositiongame.R
import com.smaglo360.compositiongame.databinding.FragmentGameBinding
import com.smaglo360.compositiongame.domain.enteties.GameResult
import com.smaglo360.compositiongame.domain.enteties.GameSettings
import com.smaglo360.compositiongame.domain.enteties.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private val viewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

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
        observeViews()
        setOnClickListeners()
        viewModel.startGame(level)
    }


    private fun observeViews() {
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.generatedQuestion.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvVisibleNumber.text = it.visibleNumber.toString()
                for (i in 0 until tvOptions.size) {
                    tvOptions[i].text = it.options[i].toString()
                }
            }
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            with(binding) {
                tvAnswerProgress.text = it
            }
        }
        viewModel.percentageOfRightAnswer.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            val colorResId = getColorByState(it)
            binding.tvAnswerProgress.setTextColor(colorResId)
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val colorResId = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(colorResId)
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameResultFragment(it)
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
    }
    private fun getColorByState(state: Boolean): Int {
        val colorResId = if (state) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun setOnClickListeners() {
        for (i in 0 until tvOptions.size) {
            tvOptions[i].setOnClickListener {
                viewModel.chooseAnswer(tvOptions[i].text.toString().toInt())
            }
        }
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