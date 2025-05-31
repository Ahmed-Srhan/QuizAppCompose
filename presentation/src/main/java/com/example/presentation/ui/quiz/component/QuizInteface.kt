package com.example.presentation.ui.quiz.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.ui.quiz.QuizState
import com.example.presentation.utils.decodeHtml

@Composable
fun QuizInterface(
    onOptionSelected: (Int) -> Unit,
    quizState: QuizState,
    qNumber: Int,
    modifier: Modifier = Modifier
) {
    val question = quizState.quiz?.question.orEmpty().decodeHtml()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "$qNumber.",
                color = colorResource(id = R.color.blue_grey),
                fontSize = 15.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = question,
                color = colorResource(id = R.color.blue_grey),
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        val optionLabels = listOf("A", "B", "C", "D")
        val options = quizState.shuffledOptions.mapIndexed { index, option ->
            optionLabels.getOrElse(index) { "" } to option.decodeHtml()
        }

        options.forEachIndexed { index, (label, text) ->
            if (text.isNotBlank()) {
                QuizOption(
                    optionNumber = label,
                    options = text,
                    selected = quizState.selectedOptions == index,
                    onOptionClick = { onOptionSelected(index) },
                    onUnselectOption = { onOptionSelected(-1) }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
