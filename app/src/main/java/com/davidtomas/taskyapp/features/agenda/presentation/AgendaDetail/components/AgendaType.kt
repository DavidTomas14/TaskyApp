package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType

@Composable
fun AgendaType(
    agendaType: AgendaType
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .background(
                    when (agendaType) {
                        AgendaType.EVENT -> MaterialTheme.colorScheme.primary
                        AgendaType.TASK -> MaterialTheme.colorScheme.secondary
                        AgendaType.REMINDER -> MaterialTheme.colorScheme.inversePrimary
                    }
                )
                .size(28.dp)
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            text = when (agendaType) {
                AgendaType.EVENT -> "Event"
                AgendaType.TASK -> "Task"
                AgendaType.REMINDER -> "Reminder"
            }
        )
    }
}

@Preview
@Composable
fun AgendaTypePreview() {
    TaskyAppTheme {
        Column {
            AgendaType(agendaType = AgendaType.EVENT)
            AgendaType(agendaType = AgendaType.TASK)
            AgendaType(agendaType = AgendaType.REMINDER)
        }
    }
}