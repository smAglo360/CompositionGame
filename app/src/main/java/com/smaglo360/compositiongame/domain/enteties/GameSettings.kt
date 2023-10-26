package com.smaglo360.compositiongame.domain.enteties

import java.io.Serializable

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentageOfRightAnswers: Int,
    val gameTimeInSeconds: Int
): Serializable
