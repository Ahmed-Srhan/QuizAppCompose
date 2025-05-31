package com.example.presentation.ui.home

data class HomeScreenState(
    val selectedNumberOfQuestions: Int = 10,
    val selectedCategory: String = "General Knowledge",
    val selectedDifficulty: String = "Easy",
    val selectedType: String = "Multiple Choice",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
