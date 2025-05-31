package com.example.domain.repository

import com.example.domain.model.QuizModel

interface QuizRepository {

    suspend fun getQuizzes(amount:Int,category:Int,difficulty:String,type: String): List<QuizModel>
}