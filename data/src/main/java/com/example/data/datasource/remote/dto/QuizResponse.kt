package com.example.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class QuizResponse(
	@SerializedName("response_code")
	val responseCode: Int? = null,

	@SerializedName("results")
	val results: List<QuizItem?>? = null
)

data class QuizItem(
	@SerializedName("difficulty")
	val difficulty: String? = null,

	@SerializedName("question")
	val question: String? = null,

	@SerializedName("correct_answer")
	val correctAnswer: String? = null,

	@SerializedName("incorrect_answers")
	val incorrectAnswers: List<String?>? = null,

	@SerializedName("type")
	val type: String? = null,

	@SerializedName("category")
	val category: String? = null
)

