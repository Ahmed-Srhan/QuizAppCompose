package com.example.presentation.ui.quiz


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import app.cash.turbine.test
import com.example.domain.Resource
import com.example.domain.model.QuizModel
import com.example.domain.use_case.GetQuizzesUseCase
import com.example.presentation.ui.model.toUIModel
import com.example.presentation.utils.decodeHtml
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    // Use the TestDispatcher for viewModelScope
    val testDispatcher = StandardTestDispatcher()

    private lateinit var getQuizzesUseCase: GetQuizzesUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var vm: QuizViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxed = true)
        getQuizzesUseCase = mockk()
        // Provide args: number=1, category="Science", difficulty="Easy", type="Multiple Choice"
        savedStateHandle = SavedStateHandle().apply {
            set("number", 1)
            set("category", "Science")
            set("difficulty", "Easy")
            set("type", "Multiple Choice")
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `init - loading then success updates quizState`() = runTest {
        // Prepare a single QuizModel
        val qm = QuizModel(
            difficulty = "easy",
            question = "Q?",
            correctAnswer = "A",
            incorrectAnswers = listOf("B", "C", "D"),
            type = "multiple",
            category = "Science"
        )
        // Mock use case to emit Loading then Success
        coEvery {
            getQuizzesUseCase(1, any(), "easy", "multiple")
        } returns flowOf(
            Resource.Loading(),
            Resource.Success(listOf(qm))
        )

        vm = QuizViewModel(getQuizzesUseCase, savedStateHandle)

        // Collect the state and assert
        vm.state.test {
            // first emission is default state
            val first = awaitItem()
            assertFalse(first.isLoading)
            assertTrue(first.quizState.isEmpty())

            // next emission: loading
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            // next emission: success with one quizState
            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertEquals(1, successState.quizState.size)
            // check that selectedOptions == -1
            assertEquals(-1, successState.quizState[0].selectedOptions)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init - loading then error sets error message`() = runTest {
        val errMsg = "Network failure"
        coEvery {
            getQuizzesUseCase(1, any(), "easy", "multiple")
        } returns flowOf(
            Resource.Loading(),
            Resource.Error<List<QuizModel>>(errMsg)
        )

        vm = QuizViewModel(getQuizzesUseCase, savedStateHandle)

        vm.state.test {
            awaitItem() // default
            awaitItem() // loading
            val errorState = awaitItem()
            assertEquals(errMsg, errorState.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `selecting correct option increments score`() = runTest {
        mockkStatic("com.example.presentation.utils.StringExtensionsKt")

        every { any<String>().decodeHtml() } answers { call.invocation.args[0] as String }

        // Build a UIModel with known shuffledOptions ordering
        val uiModel = QuizModel(
            difficulty = "easy",
            question = "Q?",
            correctAnswer = "Right",
            incorrectAnswers = listOf("X", "Y", "Z"),
            type = "multiple",
            category = "Science"
        )

        // Mock UseCase to immediately emit Success
        coEvery {
            getQuizzesUseCase(1, any(), "easy", "multiple")
        } returns flowOf(Resource.Success(listOf(uiModel)))

        vm = QuizViewModel(getQuizzesUseCase, savedStateHandle)

        // wait for the success emission
        testDispatcher.scheduler.advanceUntilIdle()

        // locate the index of correct answer in shuffledOptions
        val quizState = vm.state.value.quizState.first()
        val correctIndex = quizState.shuffledOptions.indexOf(uiModel.correctAnswer)

        // select it
        vm.onEvent(QuizScreenEvent.SetOptionSelected(quizStateIndex = 0, selectedOption = correctIndex))

        // verify score == 1
        assertEquals(1, vm.state.value.score)
    }

    @Test
    fun `onBackPressRequest and dismissExitDialog toggle dialog flag`() {
        vm = QuizViewModel(getQuizzesUseCase, savedStateHandle)
        // initial should be false
        assertFalse(vm.showExitDialog)

        vm.onBackPressRequest()
        assertTrue(vm.showExitDialog)

        vm.dismissExitDialog()
        assertFalse(vm.showExitDialog)
    }

    @Test
    fun `confirmExit resets flag and navigates home`() {
        vm = QuizViewModel(getQuizzesUseCase, savedStateHandle)
        val nav = mockk<NavController>(relaxed = true)

        // open dialog
        vm.onBackPressRequest()
        assertTrue(vm.showExitDialog)

        vm.confirmExit(nav)
        assertFalse(vm.showExitDialog)
        verify { nav.navigateToHomeScreenWithPopUp() }
    }
}


