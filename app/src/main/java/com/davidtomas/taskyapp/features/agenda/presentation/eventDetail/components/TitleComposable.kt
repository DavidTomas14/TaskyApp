package com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun TitleComposable(
    title: String,
    onNavigateToEditClick: () -> Unit,
    isEdit: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_circle_no_check),
                modifier = Modifier
                    .padding(end = 8.dp),
                contentDescription = "More Actions",
            )
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            if (isEdit)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_next),
                    modifier = Modifier
                        .clickable { onNavigateToEditClick() },
                    contentDescription = "More Actions",
                )
        }
        Spacer(modifier = Modifier.height(23.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
fun TitleComposablePreview() {
    TaskyAppTheme {
        Column {
            TitleComposable(
                title = "Meeting",
                onNavigateToEditClick = {}
            )
            TitleComposable(
                title = "Meeting",
                onNavigateToEditClick = {},
                isEdit = true
            )
        }
    }
}