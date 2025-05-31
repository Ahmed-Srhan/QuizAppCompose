package com.example.data.mapper

import com.example.data.datasource.remote.dto.QuizItem
import com.example.domain.model.QuizModel


fun QuizItem.toDomain(): QuizModel = QuizModel(
    difficulty = difficulty ?: "",
    question = question ?: "No question",
    correctAnswer = correctAnswer ?: "",
    incorrectAnswers = incorrectAnswers?.filterNotNull() ?: emptyList(),
    type = type ?: "",
    category = category ?: ""
)
