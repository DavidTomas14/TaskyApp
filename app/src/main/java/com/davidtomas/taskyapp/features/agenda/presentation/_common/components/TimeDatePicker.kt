package com.davidtomas.taskyapp.features.agenda.presentation._common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.util.formatHoursAndMinutes
import com.davidtomas.taskyapp.core.presentation.util.formatToMMDDYY
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import java.time.Duration
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDatePicker(
    zonedDateTime: ZonedDateTime,
    label: String,
    onConfirmChangedDateClick: (Long) -> Unit,
    onConfirmChangedTimeClick: (Long, Long) -> Unit,
    isEditable: Boolean
) {
    var isTimePickerVisible by remember {
        mutableStateOf(false)
    }
    var isDatePickerVisible by remember {
        mutableStateOf(false)
    }
    val timePickerState = rememberTimePickerState(
        initialHour = zonedDateTime.hour,
        initialMinute = zonedDateTime.minute,
        is24Hour = true
    )
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = zonedDateTime.toInstant().toEpochMilli()
    )
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(if (isEditable) 1f else 2f),
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier.weight(if (isEditable) 1f else 2f),
                text = formatHoursAndMinutes(timePickerState.hour, timePickerState.minute),
                style = MaterialTheme.typography.titleSmall
            )
            if (isEditable) Icon(
                painter = painterResource(R.drawable.ic_arrow_next),
                modifier = Modifier
                    .clickable { isTimePickerVisible = true },
                contentDescription = "More Actions",
            )
            Text(
                modifier = Modifier.weight(if (isEditable) 2f else 5f),
                text = datePickerState.selectedDateMillis.formatToMMDDYY(),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            if (isEditable) Icon(
                painter = painterResource(id = R.drawable.ic_arrow_next),
                modifier = Modifier
                    .clickable { isDatePickerVisible = true },
                contentDescription = "More Actions",
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        HorizontalDivider()
        if (isTimePickerVisible) {
            AlertDialogTimePicker(
                state = timePickerState,
                onCancelDialog = { isTimePickerVisible = false },
                onConfirmDialogClick = {
                    onConfirmChangedTimeClick(
                        Duration.ofHours(timePickerState.hour.toLong()).toMillis(),
                        Duration.ofMinutes(timePickerState.minute.toLong()).toMillis()
                    )
                }
            )
        }
        if (isDatePickerVisible) {
            AlertDialogDatePicker(
                state = datePickerState,
                onCancelDialog = { isDatePickerVisible = false },
                onConfirmDialogClick = {
                    onConfirmChangedDateClick(
                        datePickerState.selectedDateMillis ?: 0L
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertDialogTimePicker(
    state: TimePickerState,
    onCancelDialog: () -> Unit,
    onConfirmDialogClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancelDialog() },
        {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                TimePicker(
                    state = state,
                    colors = TimePickerDefaults.colors(),
                    layoutType = TimePickerDefaults.layoutType()
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
                            onConfirmDialogClick()
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
            Column {
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TimeDatePickerPreview() {
    TaskyAppTheme {
        Column {
            TimeDatePicker(
                label = "From",
                zonedDateTime = ZonedDateTime.now(),
                onConfirmChangedTimeClick = { _, _ -> },
                onConfirmChangedDateClick = {},
                isEditable = true
            )
            TimeDatePicker(
                label = "From",
                zonedDateTime = ZonedDateTime.now(),
                onConfirmChangedTimeClick = { _, _ -> },
                onConfirmChangedDateClick = {},
                isEditable = false
            )
        }
    }
}