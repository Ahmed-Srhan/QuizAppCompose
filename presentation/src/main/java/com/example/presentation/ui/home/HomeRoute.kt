package com.example.presentation.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "homeScreen"

fun NavController.navigateToHomeScreen(
) {
    navigate(HOME_ROUTE) {
        popUpTo(HOME_ROUTE) { inclusive = true }
    }
}

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable(
        route = HOME_ROUTE,
    ) {
        HomeScreen(
            navController = navController
        )
    }
}
