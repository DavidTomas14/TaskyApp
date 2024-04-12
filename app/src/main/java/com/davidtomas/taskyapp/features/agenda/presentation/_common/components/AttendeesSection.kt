package com.davidtomas.taskyapp.features.agenda.presentation._common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel

@Composable
fun AttendeesSection(
    attendeeList: List<AttendeeModel>,
    isEditable: Boolean,
    onAddAttendeeButtonClick: () -> Unit,
    onDeleteAttendeeIconClick: (AttendeeModel) -> Unit
) {
    var visitorsTypeSelected by remember {
        mutableStateOf(VisitorPill.ALL)
    }
    val (goingList, notGoingList) = attendeeList.partition { it.isGoing }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            text = "Visitors"
        )
        if (isEditable) {
            Image(
                painter = painterResource(id = R.drawable.ic_add_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 18.dp)
                    .alpha(0.8f)
                    .size(30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .border(
                        border = BorderStroke(2.dp, Color.Gray)
                    )
                    .clickable { onAddAttendeeButtonClick() },
                contentScale = ContentScale.Fit
            )
        }
    }
    Spacer(modifier = Modifier.height(17.dp))
    VisitorPillsSelector(
        pillSelected = visitorsTypeSelected,
        modifier = Modifier.fillMaxWidth(),
        onPillClicked = {
            visitorsTypeSelected = VisitorPill.valueOf(it.name)
        }
    )
    Spacer(modifier = Modifier.height(17.dp))

    if (visitorsTypeSelected != VisitorPill.NOT_GOING) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = "Going"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (goingList.isEmpty()) {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Empty List"
                )
            } else {
                goingList.forEach {
                    AttendeeItem(
                        fullName = it.fullName,
                        onDeleteIconClicked = {
                            onDeleteAttendeeIconClick(it)
                        },
                        isEditable = isEditable
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(17.dp))
    }
    if (visitorsTypeSelected != VisitorPill.GOING) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = "Not Going"
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (notGoingList.isEmpty()) {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Empty List"
                )
            } else {
                notGoingList.forEach {
                    AttendeeItem(
                        fullName = it.fullName,
                        onDeleteIconClicked = {
                            onDeleteAttendeeIconClick(it)
                        },
                        isEditable = isEditable
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        }
    }
