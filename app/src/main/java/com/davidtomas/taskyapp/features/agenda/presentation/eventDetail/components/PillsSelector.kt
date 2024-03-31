package com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun PillsSelector(
    modifier: Modifier = Modifier,
    onPillClicked: (Pill) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Pill.values().forEach {
            Button(
                modifier = Modifier
                    .height(35.dp)
                    .width(100.dp),
                onClick = { onPillClicked(it) }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it.text,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

enum class Pill(val text: String) {
    ALL("All"),
    GOING("Going"),
    NOT_GOING("Not going")
}

@Preview
@Composable
fun PillsSelectorPreview() {
    TaskyAppTheme {
        PillsSelector(onPillClicked = {})
    }
}