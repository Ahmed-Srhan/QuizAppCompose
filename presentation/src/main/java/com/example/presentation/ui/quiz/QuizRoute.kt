package com.example.presentation.ui.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation.ui.home.HOME_ROUTE

private const val ROUTE = "quizScreen"
fun NavController.navigateToQuizScreen(
    numOfQuestion: Int,
    category: String,
    difficulty: String,
    type: String
) {
    navigate("$ROUTE/$numOfQuestion/$category/$difficulty/$type")
}

fun NavController.navigateToHomeScreenWithPopUp() {
    navigate(HOME_ROUTE) {
        popUpTo(ROUTE) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.quizRoute(navController: NavController) {
    composable(
        route = "$ROUTE/{${QuizArgs.NUMBER}}/{${QuizArgs.CATEGORY}}/{${QuizArgs.DIFFICULTY}}/{${QuizArgs.TYPE}}",
        arguments = listOf(
            navArgument(QuizArgs.NUMBER) { type = NavType.IntType },
            navArgument(QuizArgs.CATEGORY) { type = NavType.StringType },
            navArgument(QuizArgs.DIFFICULTY) { type = NavType.StringType },
            navArgument(QuizArgs.TYPE) { type = NavType.StringType }
        )
    ) {
        QuizScreen(
            navController = navController
        )
    }
}

class QuizArgs(savedStateHandle: SavedStateHandle) {
    val number: Int = checkNotNull(savedStateHandle[NUMBER])
    val category: String = checkNotNull(savedStateHandle[CATEGORY])
    val difficulty: String = checkNotNull(savedStateHandle[DIFFICULTY])
    val type: String = checkNotNull(savedStateHandle[TYPE])

    companion object {
        const val NUMBER = "number"
        const val CATEGORY = "category"
        const val DIFFICULTY = "difficulty"
        const val TYPE = "type"
    }
}

