package com.example.presentation.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.Resource
import com.example.domain.use_case.GetQuizzesUseCase
import com.example.presentation.ui.model.QuizUIModel
import com.example.presentation.ui.model.toUIModel
import com.example.presentation.utils.Constants
import com.example.presentation.utils.decodeHtml
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizzesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(QuizScreenState())
    val state = _state.asStateFlow()

    val quizArgs: QuizArgs = QuizArgs(savedStateHandle)

    var showExitDialog by mutableStateOf(false)
        private set

    fun onBackPressRequest() {
        showExitDialog = true
    }

    fun dismissExitDialog() {
        showExitDialog = false
    }

    fun confirmExit(navController: NavController) {
        showExitDialog = false
        navController.navigateToHomeScreenWithPopUp()
    }

    init {
        print("QuizViewModel init called${quizArgs.number} ${quizArgs.category} ${quizArgs.difficulty} ${quizArgs.type}")
        val difficulty = quizArgs.difficulty.lowercase()
        val type = if (quizArgs.type == "Multiple Choice") "multiple" else "boolean"
        onEvent(
            QuizScreenEvent.GetQuiz(
                amount = quizArgs.number,
                category = Constants.categoriesMap[quizArgs.category] ?: 0,
                difficulty = difficulty,
                type = type
            )
        )
    }

    fun onEvent(event: QuizScreenEvent) {
        when (event) {
            is QuizScreenEvent.GetQuiz -> {
                fetchQuizzes(
                    amount = event.amount,
                    category = event.category,
                    difficulty = event.difficulty,
                    type = event.type
                )
            }

            is QuizScreenEvent.SetOptionSelected -> {
                updateSelectedOption(event.quizStateIndex, event.selectedOption)
            }
        }
    }

    private fun updateSelectedOption(quizStateIndex: Int, selectedOption: Int) {
        val updatedQuizStateList = _state.value.quizState.mapIndexed { index, quizState ->
            if (index == quizStateIndex) {
                quizState.copy(selectedOptions = selectedOption)
            } else quizState
        }

        _state.value = _state.value.copy(quizState = updatedQuizStateList)
        updateScore(updatedQuizStateList[quizStateIndex])
    }

    private fun updateScore(quizState: QuizState) {
        if (quizState.selectedOptions == -1) return

        val correctAnswer = quizState.quiz?.correctAnswer
        val selectedAnswer = quizState.selectedOptions.let {
            quizState.shuffledOptions[it].decodeHtml()
        }

        if (selectedAnswer == correctAnswer) {
            _state.value = _state.value.copy(score = _state.value.score + 1)
        }
    }

    private fun fetchQuizzes(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ) = viewModelScope.launch {
        getQuizzesUseCase(amount, category, difficulty, type).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    val quizzes = resource.data.orEmpty().map { it.toUIModel() }
                    _state.value = QuizScreenState(
                        quizState = quizzes.map { it.toQuizState() }
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = resource.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private fun QuizUIModel.toQuizState(): QuizState {
        val shuffledOptions = (incorrectAnswers + correctAnswer).shuffled()
        return QuizState(quiz = this, shuffledOptions = shuffledOptions, selectedOptions = -1)
    }
}
