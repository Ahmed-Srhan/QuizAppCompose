package com.example.domain.use_case

import com.example.domain.Resource
import com.example.domain.model.QuizModel
import com.example.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetQuizzesUseCase(
    val quizRepository: QuizRepository
) {
    operator fun invoke(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Flow<Resource<List<QuizModel>>> = flow {
        emit(Resource.Loading())
        try {
            val quizzes = quizRepository.getQuizzes(amount = amount,category=category, difficulty = difficulty, type = type)
            emit(Resource.Success(quizzes))
        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage?:"Unexpected error"))
        }

    }.flowOn(Dispatchers.IO)
}