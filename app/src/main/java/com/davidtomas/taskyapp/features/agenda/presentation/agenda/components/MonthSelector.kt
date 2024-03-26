package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun MonthSelector(
    month: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
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
    }
}

@Preview
@Composable
fun MonthSelectorPreview() {
    TaskyAppTheme {
        MonthSelector(
            modifier = Modifier.background(Color.Black),
            month = "March"
        )
    }
}