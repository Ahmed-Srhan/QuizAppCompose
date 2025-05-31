package com.example.presentation.ui.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.presentation.ui.home.HOME_ROUTE
import com.example.presentation.ui.home.homeRoute
import com.example.presentation.ui.quiz.quizRoute
import com.example.presentation.ui.score.screenRoute


@Composable
fun QuizNavGraph(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        homeRoute(navController)
        quizRoute(navController)
        screenRoute(navController)
    }
}