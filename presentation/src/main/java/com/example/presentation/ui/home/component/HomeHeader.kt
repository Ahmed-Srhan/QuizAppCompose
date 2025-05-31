package com.example.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R


@Composable
fun HomeHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                color = colorResource(R.color.dark_slate_blue),
                shape = RoundedCornerShape(
                    bottomEnd = 50.dp,
                    bottomStart = 50.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .size(35.dp),
                tint = colorResource(id = R.color.blue_grey)
            )

            Text(
                text = "Quiz App",
                color = colorResource(id = R.color.blue_grey),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(3.5f),
                textAlign = TextAlign.Center,
                fontSize = 35.sp
            )

            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .size(35.dp),
                tint = colorResource(id = R.color.blue_grey)
            )


        }

    }
}