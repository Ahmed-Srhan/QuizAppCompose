package com.example.presentation.ui.quiz

sealed class QuizScreenEvent {
    data class GetQuiz(
        val amount: Int,
        val category: Int,
        val difficulty: String,
        val type: String
    ) : QuizScreenEvent()

    data class SetOptionSelected(val quizStateIndex: Int, val selectedOption: Int) :
        QuizScreenEvent()
}