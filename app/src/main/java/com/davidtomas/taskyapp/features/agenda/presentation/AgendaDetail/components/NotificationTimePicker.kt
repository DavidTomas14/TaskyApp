package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.util.hourToMillis
import com.davidtomas.taskyapp.core.presentation.util.minuteToMillis
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.DropDownItems
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.DropDownOptions

@Composable
fun NotificationTimePicker(
    text: String,
    onOptionSelected: (Long) -> Unit,
    isEditable: Boolean
) {
    var isDropdownMenuShown by remember {
        mutableStateOf(false)
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = "More Actions",
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                text = text,
                style = MaterialTheme.typography.titleSmall
            )
            if (isEditable)
                Box() {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_next),
                        modifier = Modifier
                            .clickable { isDropdownMenuShown = true },
                        contentDescription = "More Actions",
                    )
                    DropDownOptions(
                        isShown = isDropdownMenuShown,
                        onDismissRequest = { isDropdownMenuShown = false },
                        dropDownItems = listOf(
                            DropDownItems(
                                text = "10 minutes before",
                                onClick = {
                                    onOptionSelected(10.minuteToMillis())
                                    isDropdownMenuShown = false
                                }
                            ),
                            DropDownItems(
                                text = "30 minutes before",
                                onClick = {
                                    onOptionSelected(30.minuteToMillis())
                                    isDropdownMenuShown = false
                                }
                            ),
                            DropDownItems(
                                text = "1 hour before",
                                onClick = {
                                    onOptionSelected(1.hourToMillis())
                                    isDropdownMenuShown = false
                                }
                            ),
                            DropDownItems(
                                text = "6 hours before",
                                onClick = {
                                    onOptionSelected(6.hourToMillis())
                                    isDropdownMenuShown = false
                                }
                            ),
                            DropDownItems(
                                text = "1 day before",
                                onClick = {
                                    onOptionSelected(24.hourToMillis())
                                    isDropdownMenuShown = false
                                }
                            ),
                        )
                    )
                }

        }
        Spacer(modifier = Modifier.height(23.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
fun NotificationTimePickerPreview() {
    TaskyAppTheme {
        Column {
            NotificationTimePicker(
                text = "30 minutes before",
                onOptionSelected = { /*TODO*/ },
                isEditable = false
            )
            NotificationTimePicker(
                text = "30 minutes before",
                onOptionSelected = { /*TODO*/ },
                isEditable = true
            )
        }
    }
}