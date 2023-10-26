package com.smaglo360.compositiongame.data

import com.smaglo360.compositiongame.domain.enteties.GameSettings
import com.smaglo360.compositiongame.domain.enteties.Level
import com.smaglo360.compositiongame.domain.enteties.Question
import com.smaglo360.compositiongame.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_VISIBLE_NUM_VALUE = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUM_VALUE, sum)
        val rightAnswer = sum - visibleNumber
        val options = HashSet<Int>()
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_SUM_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }

            Level.EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    90
                )
            }

            Level.MEDIUM -> {
                GameSettings(
                    15,
                    10,
                    80,
                    75
                )
            }

            Level.HARD -> {
                GameSettings(
                    20,
                    15,
                    90,
                    60
                )
            }
        }
    }
}