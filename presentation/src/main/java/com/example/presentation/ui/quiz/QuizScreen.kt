package com.example.presentation.ui.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.presentation.R
import com.example.presentation.ui.common.ButtonBox
import com.example.presentation.ui.common.ErrorMessage
import com.example.presentation.ui.common.QuizAppBar
import com.example.presentation.ui.quiz.component.QuizInterface
import com.example.presentation.ui.quiz.component.ShimmerQuizInterface
import com.example.presentation.ui.score.navigateToScoreScreen
import kotlinx.coroutines.launch


@Composable
fun QuizScreen(
    navController: NavController
) {
    val viewModel: QuizViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event = viewModel::onEvent
    val navArgs = viewModel.quizArgs
    val showExitDialog = viewModel.showExitDialog
    BackHandler {
        viewModel.onBackPressRequest()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        QuizAppBar(quizCategory = navArgs.category) {
            viewModel.onBackPressRequest()
        }
        Spacer(
            modifier = Modifier
                .height(30.dp)
        )
        AnimatedVisibility(visible = quizFetched(state)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Questions :${navArgs.number}", color = colorResource(R.color.blue_grey))
                    Text(navArgs.difficulty, color = colorResource(R.color.blue_grey))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = colorResource(R.color.blue_grey))
                )
                Spacer(modifier = Modifier.height(30.dp))

                val pagerState = rememberPagerState { state.quizState.size }
                HorizontalPager(state = pagerState) { index ->
                    QuizInterface(
                        modifier = Modifier.weight(1f),
                        quizState = state.quizState[index],
                        onOptionSelected = { selectedIndex ->
                            event(
                                QuizScreenEvent.SetOptionSelected(
                                    quizStateIndex = index,
                                    selectedOption = selectedIndex,
                                )
                            )
                        },
                        qNumber = index + 1
                    )
                }
                val buttonText by remember {
                    derivedStateOf {
                        when (pagerState.currentPage) {
                            0 -> {
                                listOf("", "Next")
                            }

                            (state.quizState.size - 1) -> {
                                listOf("Previous", "Submit")
                            }

                            else -> {
                                listOf("Previous", "Next")
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 50.dp)
                        .navigationBarsPadding()

                ) {
                    val scope = rememberCoroutineScope()
                    if (buttonText[0].isNotEmpty()) {
                        ButtonBox(
                            text = "Previous",
                            padding = 8.dp,
                            fraction = 0.45f,
                            fontSize = 16.sp
                        ) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        }
                    }
                    ButtonBox(
                        text = buttonText[1],
                        padding = 8.dp,
                        borderColor = colorResource(id = R.color.orange),
                        containerColor = if (pagerState.currentPage == state.quizState.size - 1) colorResource(
                            id = R.color.orange
                        ) else colorResource(id = R.color.dark_slate_blue),
                        fraction = 1f,
                        textColor = colorResource(id = R.color.white),
                        fontSize = 16.sp
                    ) {
                        if (pagerState.currentPage == state.quizState.size - 1) {
                            navController.navigateToScoreScreen(
                                numOfQuestion = state.quizState.size,
                                correctAnswer = state.score
                            )
                        } else {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        }
                    }
                }

            }

        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissExitDialog() },
            title = { Text("Exit Quiz?") },
            text = { Text("Are you sure you want to leave the quiz? Your progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.confirmExit(navController)
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.dismissExitDialog()
                }) {
                    Text("No")
                }
            }
        )
    }

}

@Composable
private fun quizFetched(state: QuizScreenState): Boolean {
    return when {
        state.isLoading -> {
            ShimmerQuizInterface()
            return false
        }

        state.quizState.isNotEmpty() -> {
            return true
        }

        else -> {
            ErrorMessage(message = state.score.toString())
            return false
        }

    }
}


