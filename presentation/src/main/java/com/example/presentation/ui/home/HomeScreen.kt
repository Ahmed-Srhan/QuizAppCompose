package com.example.presentation.ui.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.presentation.ui.home.component.HomeScreenContent
import com.example.presentation.ui.quiz.navigateToQuizScreen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is HomeScreenEffect.NavigateToQuiz -> {
                    navController.navigateToQuizScreen(
                        numOfQuestion = effect.state.selectedNumberOfQuestions,
                        category = effect.state.selectedCategory,
                        difficulty = effect.state.selectedDifficulty,
                        type = effect.state.selectedType
                    )
                }

                is HomeScreenEffect.ShowError -> {
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    HomeScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )


}

