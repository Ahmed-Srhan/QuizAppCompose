package com.example.presentation.ui.model

import com.example.domain.model.QuizModel

data class QuizUIModel(
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val type: String,
    val category: String
)

fun QuizModel.toUIModel(): QuizUIModel {
    return QuizUIModel(
        difficulty,
        question,
        correctAnswer,
        incorrectAnswers,
        type,
        category
    )
}