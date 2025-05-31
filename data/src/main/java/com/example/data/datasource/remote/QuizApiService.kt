package com.example.data.datasource.remote

import com.example.data.datasource.remote.dto.QuizResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {
    @GET("api.php")
    suspend fun getQuiz(
        @Query("amount") amount:Int,
        @Query("category") category:Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String,
        ): QuizResponse
}