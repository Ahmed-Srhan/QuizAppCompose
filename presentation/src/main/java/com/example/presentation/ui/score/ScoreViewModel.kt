package com.example.presentation.ui.score

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = ScoreArgs(savedStateHandle)

    val numOfQuestions: Int = args.number
    val numOfCorrectAnswers: Int = args.correctAnswer

    val scorePercentage: Int = calculatePercentage(numOfCorrectAnswers, numOfQuestions)

    val annotatedResult: AnnotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append("You attempted ")
        }
        withStyle(style = SpanStyle(color = Color.Blue)) {
            append("$numOfQuestions questions ")
        }
        withStyle(style = SpanStyle(color = Color.Black)) {
            append("and from that ")
        }
        withStyle(style = SpanStyle(color = Color.Green)) {
            append("$numOfCorrectAnswers answer")
        }
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(" are Correct")
        }
    }

    private fun calculatePercentage(correct: Int, total: Int): Int {
        return if (total == 0) 0 else ((correct.toDouble() / total) * 100).toInt()
    }
}
