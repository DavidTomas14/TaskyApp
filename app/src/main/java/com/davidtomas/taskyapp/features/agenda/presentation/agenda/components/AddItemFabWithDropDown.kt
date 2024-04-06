package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.DropDownItems
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.DropDownOptions

@Composable
fun AddItemFabWithDropdown(
    onDropDownOptionSelected: (AgendaType) -> Unit
) {
    var isDropDownMenuShown by remember {
        mutableStateOf(false)
    }
    FloatingActionButton(
        onClick = { isDropDownMenuShown = true }
    ) {
        Box {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_icon),
                contentDescription = ""
            )
            DropDownAgendaItemOptions(
                isShown = isDropDownMenuShown,
                onDismissRequest = { isDropDownMenuShown = false },
                dropDownItems = listOf(
                    DropDownItems(
                        text = "Event",
                        leadingIcon = R.drawable.ic_add_event,
                        onClick = {
                            onDropDownOptionSelected(AgendaType.EVENT)
                            isDropDownMenuShown = false
                        }
                    ),
                    DropDownItems(
                        text = "Task",
                        leadingIcon = R.drawable.ic_add_task,
                        onClick = {
                            onDropDownOptionSelected(AgendaType.TASK)
                            isDropDownMenuShown = false
                        }
                    ),
                    DropDownItems(
                        text = "Reminder",
                        leadingIcon = R.drawable.ic_add_reminder,
                        onClick = {
                            onDropDownOptionSelected(AgendaType.REMINDER)
                            isDropDownMenuShown = false
                        }
                    )
                )
            )
        }
    }
}