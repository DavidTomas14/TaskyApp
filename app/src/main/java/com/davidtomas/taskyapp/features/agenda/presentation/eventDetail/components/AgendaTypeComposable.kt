package com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun AgendaTypeComposable(
    title: String,
    rectangleColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .background(rectangleColor)
                .size(28.dp)
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            text = title,
        )
    }
}

@Preview
@Composable
fun AgendaTypeComposablePreview() {
    TaskyAppTheme {
        Column {
            AgendaTypeComposable(
                title = "Meeting",
                rectangleColor = Color.Blue,
            )
            AgendaTypeComposable(
                title = "Meeting",
                rectangleColor = Color.Gray,
            )
        }
    }
}