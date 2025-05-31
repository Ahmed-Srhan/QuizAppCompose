package com.example.presentation.ui.home

sealed class HomeScreenEvent {
    data class SelectNumberOfQuestions(val value: Int) : HomeScreenEvent()
    data class SelectCategory(val value: String) : HomeScreenEvent()
    data class SelectDifficulty(val value: String) : HomeScreenEvent()
    data class SelectType(val value: String) : HomeScreenEvent()
    data object GenerateQuiz : HomeScreenEvent()
}

sealed class HomeScreenEffect {
    data class NavigateToQuiz(val state: HomeScreenState) : HomeScreenEffect()
    data class ShowError(val message: String) : HomeScreenEffect()

}