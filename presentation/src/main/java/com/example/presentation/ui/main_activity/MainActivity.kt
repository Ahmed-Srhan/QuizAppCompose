package com.example.presentation.ui.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.presentation.R
import com.example.presentation.theme.QuizAppComposeTheme
import com.example.presentation.ui.nav_graph.QuizNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            QuizAppComposeTheme {
                Box(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .background(
                            color = colorResource(R.color.mid_night_blue)
                        ),
                    contentAlignment = Alignment.Companion.TopCenter
                ) {
                    val navController = rememberNavController()
                    QuizNavGraph(
                        navController
                    )
                }
            }
        }
    }
}