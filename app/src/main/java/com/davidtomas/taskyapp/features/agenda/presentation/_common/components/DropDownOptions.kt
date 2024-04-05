package com.davidtomas.taskyapp.features.agenda.presentation._common.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun DropDownOptions(
    onDismissRequest: () -> Unit,
    isShown: Boolean,
    dropDownItems: List<DropDownItems>
) {
    DropdownMenu(
        expanded = isShown,
        onDismissRequest = onDismissRequest
    ) {

        dropDownItems.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(item.text)
                },
                onClick = {
                    item.onClick()
                },
                leadingIcon = item.leadingIcon?.let {
                    {
                        Icon(
                            painter = painterResource(id = item.leadingIcon),
                            contentDescription = "More Actions"
                        )
                    }
                }
            )
        }
    }
}

data class DropDownItems(
    val text: String,
    val onClick: () -> Unit,
    @DrawableRes val leadingIcon: Int? = null
)

@Preview(showSystemUi = true)
@Composable
fun DropDownAgendaItemOptionsPreview() {
    TaskyAppTheme {
        var isShown by remember {
            mutableStateOf(false)
        }
        Button(
            onClick = {
                isShown = true
            }
        ) {
            Text(text = "Show DropDown")
        }
        DropDownOptions(
            isShown = isShown,
            onDismissRequest = {
                isShown = false
            },
            dropDownItems = listOf(
                DropDownItems(
                    text = "Open",
                    leadingIcon = R.drawable.ic_open_action,
                    onClick = {}
                ),
                DropDownItems(
                    text = "Edit",
                    leadingIcon = R.drawable.ic_edit_action,
                    onClick = {}
                ),
                DropDownItems(
                    text = "Delete",
                    leadingIcon = R.drawable.ic_delete_action,
                    onClick = {}
                )
            )
        )
    }
}