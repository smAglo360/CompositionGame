package com.smaglo360.compositiongame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smaglo360.compositiongame.R
import com.smaglo360.compositiongame.data.GameRepositoryImpl
import com.smaglo360.compositiongame.domain.enteties.GameResult
import com.smaglo360.compositiongame.domain.enteties.GameSettings
import com.smaglo360.compositiongame.domain.enteties.Level
import com.smaglo360.compositiongame.domain.enteties.Question
import com.smaglo360.compositiongame.domain.usecase.GenerateQuestionUseCase
import com.smaglo360.compositiongame.domain.usecase.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var level: Level

    private lateinit var gameSettings: GameSettings

    private val context = application

    private var timer: CountDownTimer? = null

    private val _generatedQuestion = MutableLiveData<Question>()
    val generatedQuestion: LiveData<Question>
        get() = _generatedQuestion

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _percentageOfRightAnswers = MutableLiveData<Int>()
    val percentageOfRightAnswer: LiveData<Int>
        get() = _percentageOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val gameRepositoryImpl: GameRepositoryImpl = GameRepositoryImpl

    private val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepositoryImpl)
    private val generateQuestionUseCase = GenerateQuestionUseCase(gameRepositoryImpl)
    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = generatedQuestion.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val percent = calculatePercentageOfRightAnswers()
        _percentageOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.answer_progress),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value =
            percent >= gameSettings.minPercentageOfRightAnswers

    }

    private fun calculatePercentageOfRightAnswers(): Int {
        return if (countOfRightAnswers != 0) {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        } else {
            0
        }
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        gameSettings = getGameSettingsUseCase(this.level)
        _minPercent.value = gameSettings.minPercentageOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(milisUntilFinished: Long) {
                _formattedTime.value = formatTime(milisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun generateQuestion() {
        _generatedQuestion.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}