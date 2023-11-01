package com.smaglo360.compositiongame.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.smaglo360.compositiongame.R
import com.smaglo360.compositiongame.domain.enteties.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, minCountOfRightAnswers: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.require_answers),
        minCountOfRightAnswers
    )
}

@BindingAdapter("yourScore")
fun bindYourScore(textView: TextView, countOfRightAnswers: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.your_score),
        countOfRightAnswers
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, requiredPercentage: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage_answers),
        requiredPercentage
    )
}

@BindingAdapter("percentageOfRightAnswers")
fun bindPercentageOfRightAnswers(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.percentage_right_answers),
        getPercentOfRightAnswer(gameResult)
    )
}

@BindingAdapter("resultEmoji")
fun bindEmojiResult(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

private fun getPercentOfRightAnswer(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_success
    } else {
        R.drawable.ic_fail
    }
}
