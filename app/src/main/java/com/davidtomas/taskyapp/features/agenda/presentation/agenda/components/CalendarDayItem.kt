package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.coreUi.calendarDaysColor

@Composable
fun CalendarDayItem(
    isDaySelected: Boolean,
    weekDay: String,
    monthDay: String,
    onDateSelected: () -> Unit,
    modifier: Modifier = Modifier

) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                onDateSelected()
            }
            .background(if (isDaySelected) calendarDaysColor else Color.White)
            .padding(vertical = 5.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = weekDay,
            color = Color.Black.copy(alpha = if (isDaySelected) 1f else 0.3f)
        )
        Text(text = monthDay)
    }
}

@Preview
@Composable
fun CalendarDayItemPreview() {
    TaskyAppTheme {
        Column {
            CalendarDayItem(
                weekDay = "S",
                monthDay = "5",
                isDaySelected = false,
                onDateSelected = {}
            )
            CalendarDayItem(
                weekDay = "S",
                monthDay = "5",
                isDaySelected = true,
                onDateSelected = {}
            )
        }
    }
}