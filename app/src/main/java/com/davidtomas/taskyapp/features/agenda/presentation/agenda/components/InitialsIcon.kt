package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun InitialsIcon(
    initials: String
) {
    Row(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = initials,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(backgroundColor = 0xFF020202, showSystemUi = true, showBackground = false)
@Composable
fun InitialsIconPreview() {
    TaskyAppTheme {
        InitialsIcon(initials = "AB")
    }
}