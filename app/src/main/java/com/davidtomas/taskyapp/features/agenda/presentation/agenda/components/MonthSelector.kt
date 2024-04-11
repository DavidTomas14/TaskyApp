package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthSelector(
    month: String,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDatePickerShown by remember {
        mutableStateOf(false)
    }
    var datePicker = rememberDatePickerState()
    Row(
        modifier = modifier.clickable {
            isDatePickerShown = true
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = month,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "More Actions"
        )
        if (isDatePickerShown)
            AlertDialogDatePicker(
                state = datePicker,
                onCancelDialog = {
                    isDatePickerShown = false
                },
                onConfirmDialogClick = {
                    isDatePickerShown = false
                    onDateSelected(datePicker.selectedDateMillis ?: 0L)
                }
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertDialogDatePicker(
    state: DatePickerState,
    onCancelDialog: () -> Unit,
    onConfirmDialogClick: (Long) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancelDialog() },
        {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                DatePicker(
                    state = state,
                    colors = DatePickerDefaults.colors(),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onCancelDialog() }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onConfirmDialogClick((state.selectedDateMillis ?: 0L))
                            onCancelDialog()
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun MonthSelectorPreview() {
    TaskyAppTheme {
        MonthSelector(
            modifier = Modifier.background(Color.Black),
            month = "March",
            onDateSelected = {}
        )
    }
}