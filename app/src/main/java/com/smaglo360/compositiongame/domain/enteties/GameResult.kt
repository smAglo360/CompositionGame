package com.smaglo360.compositiongame.domain.enteties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
): Parcelable
