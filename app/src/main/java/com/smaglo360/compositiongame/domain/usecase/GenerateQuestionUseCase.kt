package com.smaglo360.compositiongame.domain.usecase

import com.smaglo360.compositiongame.domain.enteties.Question
import com.smaglo360.compositiongame.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(maxSumValue: Int): Question {
        return gameRepository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        const val COUNT_OF_OPTIONS = 6
    }

}