package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components

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
fun NotificationTimePicker(
    text: String,
    onNavigateToEditClick: () -> Unit,
    isEditable: Boolean
) {
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
fun NotificationTimePickerPreview() {
    TaskyAppTheme {
        Column {
            NotificationTimePicker(
                text = "30 minutes before",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = false
            )
            NotificationTimePicker(
                text = "30 minutes before",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = true
            )
        }
    }
}