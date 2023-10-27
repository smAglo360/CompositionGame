package com.smaglo360.compositiongame.domain.enteties

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
) {
    val rightAnswer = sum - visibleNumber
}
