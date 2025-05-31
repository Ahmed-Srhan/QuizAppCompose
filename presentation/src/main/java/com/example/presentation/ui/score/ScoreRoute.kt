package com.example.presentation.ui.score

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "scoreScreen"
fun NavController.navigateToScoreScreen(
    numOfQuestion: Int,
    correctAnswer: Int,
) {
    navigate("$ROUTE/$numOfQuestion/$correctAnswer")
}

fun NavGraphBuilder.screenRoute(navController: NavController) {

    composable(
        route = "$ROUTE/{${ScoreArgs.ARG_KEY_NUM_QUESTIONS}}/{${ScoreArgs.ARG_KEY_CORRECT_ANS}}",
        arguments = listOf(
            navArgument(ScoreArgs.ARG_KEY_NUM_QUESTIONS) { type = NavType.IntType },
            navArgument(ScoreArgs.ARG_KEY_CORRECT_ANS) { type = NavType.IntType },
        )
    ) {
        ScoreScreen(
            navController = navController
        )
    }
}

class ScoreArgs(savedStateHandle: SavedStateHandle) {
    val number: Int = checkNotNull(savedStateHandle[ARG_KEY_NUM_QUESTIONS])
    val correctAnswer: Int = checkNotNull(savedStateHandle[ARG_KEY_CORRECT_ANS])

    companion object {
        const val ARG_KEY_NUM_QUESTIONS = "number_of_question"
        const val ARG_KEY_CORRECT_ANS = "correct_ans"

    }
}

