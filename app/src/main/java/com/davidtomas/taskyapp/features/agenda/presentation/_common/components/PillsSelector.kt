package com.davidtomas.taskyapp.features.agenda.presentation._common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun VisitorPillsSelector(
    pillSelected: VisitorPill,
    onPillClicked: (VisitorPill) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        VisitorPill.values().forEach {
            Button(
                modifier = Modifier
                    .height(35.dp)
                    .width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (it == pillSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = if (it == pillSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSecondaryContainer
                ),
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

enum class VisitorPill(val text: String) {
    ALL("All"),
    GOING("Going"),
    NOT_GOING("Not going")
}

@Preview
@Composable
fun PillsSelectorPreview() {
    TaskyAppTheme {
        var visitorsTypeSelected by remember {
            mutableStateOf(VisitorPill.ALL)
        }
        VisitorPillsSelector(
            pillSelected = visitorsTypeSelected,
            onPillClicked = {
                visitorsTypeSelected = it
            }
        )
    }
}