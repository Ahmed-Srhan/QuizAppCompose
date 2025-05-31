package com.example.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeScreenEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.SelectNumberOfQuestions ->
                _state.update { it.copy(selectedNumberOfQuestions = event.value) }

            is HomeScreenEvent.SelectCategory ->
                _state.update { it.copy(selectedCategory = event.value) }

            is HomeScreenEvent.SelectDifficulty ->
                _state.update { it.copy(selectedDifficulty = event.value) }

            is HomeScreenEvent.SelectType ->
                _state.update { it.copy(selectedType = event.value) }

            is HomeScreenEvent.GenerateQuiz -> {
                val currentState = _state.value

                val isValid = currentState.selectedNumberOfQuestions > 0 &&
                        currentState.selectedCategory.isNotBlank() &&
                        currentState.selectedDifficulty.isNotBlank() &&
                        currentState.selectedType.isNotBlank()
                viewModelScope.launch {
                    if (isValid) {
                        _uiEffect.emit(HomeScreenEffect.NavigateToQuiz(currentState))

                    } else {
                        _uiEffect.emit(HomeScreenEffect.ShowError("Please fill all quiz options"))
                    }
                }
            }
        }
    }

}