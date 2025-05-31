package com.example.presentation.ui.quiz

import com.example.presentation.ui.model.QuizUIModel

data class QuizScreenState(
    val isLoading: Boolean = false,
    val quizState: List<QuizState> = emptyList(),
    val error: String = "",
    val score: Int = 0
)

data class QuizState(
    val quiz: QuizUIModel? = null,
    val shuffledOptions: List<String> = emptyList(),
    val selectedOptions: Int = -1
)