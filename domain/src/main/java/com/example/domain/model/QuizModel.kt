package com.example.domain.model

data class QuizModel (
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val type: String,
    val category: String
)
