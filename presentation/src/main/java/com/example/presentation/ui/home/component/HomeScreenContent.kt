package com.example.presentation.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.common.AppDropDownMenu
import com.example.presentation.ui.common.ButtonBox
import com.example.presentation.ui.home.HomeScreenEvent
import com.example.presentation.ui.home.HomeScreenState
import com.example.presentation.utils.Constants.categories
import com.example.presentation.utils.Constants.difficulty
import com.example.presentation.utils.Constants.numbersAsString
import com.example.presentation.utils.Constants.type

@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    Column {

        HomeHeader()

        Spacer(modifier = Modifier.height(18.dp))
        AppDropDownMenu(
            menuName = "Number of Questions:", menuList = numbersAsString,
            selectedValue = state.selectedNumberOfQuestions.toString(),
            onDropDownSelected = { onEvent(HomeScreenEvent.SelectNumberOfQuestions(it.toInt())) }
        )

        Spacer(modifier = Modifier.height(8.dp))
        AppDropDownMenu(
            menuName = "Select Category:", menuList = categories,
            selectedValue = state.selectedCategory,
            onDropDownSelected = { onEvent(HomeScreenEvent.SelectCategory(it)) }
        )

        Spacer(modifier = Modifier.height(8.dp))
        AppDropDownMenu(
            menuName = "Select Difficulty:", menuList = difficulty,
            selectedValue = state.selectedDifficulty,
            onDropDownSelected = { onEvent(HomeScreenEvent.SelectDifficulty(it)) }
        )

        Spacer(modifier = Modifier.height(8.dp))
        AppDropDownMenu(
            menuName = "Select Type:", menuList = type,
            selectedValue = state.selectedType,
            onDropDownSelected = { onEvent(HomeScreenEvent.SelectType(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonBox(text = "Generate Quiz", padding = 16.dp) {
            onEvent(HomeScreenEvent.GenerateQuiz)
        }

    }

}