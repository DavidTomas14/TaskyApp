package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AttendeesSection(
    isEditable: Boolean
) {
    Text(
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        text = "Visitors"
    )
    Spacer(modifier = Modifier.height(17.dp))
    PillsSelector(
        modifier = Modifier.fillMaxWidth(),
        onPillClicked = {}
    )
    Spacer(modifier = Modifier.height(17.dp))
    Text(
        style = MaterialTheme.typography.titleSmall,
        text = "Going"
    )
    Spacer(modifier = Modifier.height(12.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AttendeeItem(
            fullName = "Ann Allen",
            onDeleteIconClicked = { /*TODO*/ },
            isEditable = isEditable
        )
        AttendeeItem(
            fullName = "Wade Warren",
            onDeleteIconClicked = { /*TODO*/ },
            isEditable = isEditable
        )
        AttendeeItem(
            fullName = "Esther Howard",
            onDeleteIconClicked = { /*TODO*/ },
            isEditable = isEditable
        )
    }
    Spacer(modifier = Modifier.height(17.dp))
    Text(
        style = MaterialTheme.typography.titleSmall,
        text = "Not Going"
    )
    Spacer(modifier = Modifier.height(12.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AttendeeItem(
            fullName = "Ann Allen",
            onDeleteIconClicked = { /*TODO*/ },
            isEditable = isEditable
        )
        AttendeeItem(
            fullName = "Wade Warren",
            onDeleteIconClicked = { /*TODO*/ },
            isEditable = isEditable
        )
        AttendeeItem(
            fullName = "Esther Howard",
            onDeleteIconClicked = { /*TODO*/ },
            isEditable = isEditable
        )
    }
    Spacer(modifier = Modifier.height(25.dp))
}