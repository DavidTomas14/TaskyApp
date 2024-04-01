package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.util.toInitials
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.InitialsIcon

@Composable
fun AttendeeItem(
    fullName: String,
    onDeleteIconClicked: () -> Unit,
    isCreator: Boolean = false,
    isEditable: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 9.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsIcon(initials = fullName.toInitials())
        Text(
            modifier = Modifier
                .padding(15.dp)
                .weight(1f),
            text = fullName,
            style = MaterialTheme.typography.titleSmall
        )
        if (isCreator)
            Text(
                text = "creator",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiary
            )
        if (isEditable)
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_action),
                modifier = Modifier
                    .clickable { onDeleteIconClicked() },
                contentDescription = "More Actions",
            )
    }
}

@Preview
@Composable
fun AttendeeItemPreview() {
    TaskyAppTheme {
        Column {
            AttendeeItem(
                fullName = "David Tomas",
                onDeleteIconClicked = { /*TODO*/ },
                isCreator = true,
                isEditable = false
            )
            AttendeeItem(
                fullName = "David Tomas",
                onDeleteIconClicked = { /*TODO*/ },
                isEditable = true
            )
        }
    }
}