package com.example.domain.use_case

import com.example.domain.Resource
import com.example.domain.model.QuizModel
import com.example.domain.repository.QuizRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals


class GetQuizzesUseCaseTest {

    private lateinit var repository: QuizRepository
    private lateinit var useCase: GetQuizzesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetQuizzesUseCase(repository)
    }

    @Test
    fun `invoke emits Loading then Success`() = runTest {
        val mockList = listOf(QuizModel("easy", "Q?", "A", listOf("B", "C"), "multiple", "General"))
        coEvery { repository.getQuizzes(any(), any(), any(), any()) } returns mockList

        val result = useCase(5, 9, "easy", "multiple").toList()

        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Success)
        assertEquals(mockList, (result[1] as Resource.Success).data)
    }

    @Test
    fun `invoke emits Loading then Error on exception`() = runTest {
        coEvery { repository.getQuizzes(any(), any(), any(), any()) } throws RuntimeException("Network error")

        val result = useCase(5, 9, "easy", "multiple").toList()

        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Error)
        assertEquals("Network error", (result[1] as Resource.Error).message)
    }
}