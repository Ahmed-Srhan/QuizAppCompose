package com.example.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.presentation.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropDownMenu(
    menuName: String,
    menuList: List<String>,
    selectedValue: String,
    onDropDownSelected: (String) -> Unit
) {
    if (menuList.isEmpty()) return
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = menuName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.blue_grey)
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {}, // Read-only
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.dark_slate_blue),
                    unfocusedContainerColor = colorResource(id = R.color.dark_slate_blue),
                    focusedTextColor = colorResource(id = R.color.blue_grey),
                    unfocusedTextColor = colorResource(id = R.color.blue_grey),
                    focusedTrailingIconColor = colorResource(id = R.color.orange),
                    unfocusedTrailingIconColor = colorResource(id = R.color.orange),
                    focusedIndicatorColor = colorResource(id = R.color.dark_slate_blue),
                    unfocusedIndicatorColor = colorResource(id = R.color.dark_slate_blue),
                ),
                shape = RoundedCornerShape(15.dp)
            )


            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier.background(colorResource(R.color.mid_night_blue))
            ) {
                menuList.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        onClick = {
                            onDropDownSelected(menuList[index])
                            isExpanded = false
                        },
                        text = { Text(text = text, color = colorResource(R.color.blue_grey)) },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}
