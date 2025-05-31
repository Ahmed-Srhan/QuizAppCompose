package com.example.data.repository_imp

import com.example.data.datasource.remote.QuizApiService
import com.example.data.mapper.toDomain
import com.example.domain.model.QuizModel
import com.example.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    val apiService: QuizApiService
): QuizRepository {

    override suspend fun getQuizzes(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): List<QuizModel> = apiService.getQuiz(
        amount = amount,
        category=category,
        difficulty=difficulty,
        type=type
    ).results?.mapNotNull { it?.toDomain() } ?: emptyList()
}
