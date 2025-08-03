package com.example.data.repository_imp

import com.example.data.datasource.remote.QuizApiService
import com.example.data.datasource.remote.dto.QuizItem
import com.example.data.datasource.remote.dto.QuizResponse
import com.example.domain.repository.QuizRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizRepositoryImplTest {

    private lateinit var apiService: QuizApiService
    private lateinit var repository: QuizRepository

    @Before
    fun setup() {
        apiService = mockk()
        repository = QuizRepositoryImpl(apiService)
    }

    @Test
    fun `getQuizzes returns mapped list when API success`() = runTest {
        val quizItem = QuizItem(
            question = "Capital of France?",
            correctAnswer = "Paris",
            incorrectAnswers = listOf("London", "Berlin", "Madrid"),
            category = "General",
            difficulty = "easy",
            type = "multiple"
        )

        val response = QuizResponse(results = listOf(quizItem))
        coEvery { apiService.getQuiz(any(), any(), any(), any()) } returns response

        val result = repository.getQuizzes(5, 9, "easy", "multiple")

        assertEquals(1, result.size)
        assertEquals("Paris", result[0].correctAnswer)
    }

    @Test
    fun `getQuizzes returns empty list when API returns null results`() = runTest {
        coEvery { apiService.getQuiz(any(), any(), any(), any()) } returns QuizResponse(results = null)

        val result = repository.getQuizzes(5, 9, "easy", "multiple")

        assertTrue(result.isEmpty())
    }
}
