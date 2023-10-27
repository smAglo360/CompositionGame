package com.smaglo360.compositiongame.domain.enteties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {
    TEST, EASY, MEDIUM, HARD
}