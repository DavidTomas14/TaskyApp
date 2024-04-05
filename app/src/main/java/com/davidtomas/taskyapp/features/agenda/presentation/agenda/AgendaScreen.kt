package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.CalendarDayItem
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.CardItem2
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.InitialsIcon
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.MonthSelector
import java.time.DayOfWeek
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AgendaScreen(
    state: AgendaState,
    onAction: (AgendaAction) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_reminder),
                    contentDescription = " "
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            val spacing = LocalSpacing.current
            Header(
                modifier = Modifier.padding(vertical = spacing.spaceSmall),
                leadingComposable = {
                    MonthSelector(
                        month = "March"
                    )
                },
                trailingComposable = {
                    InitialsIcon(initials = "AB")
                }
            )
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DayOfWeek.values().forEach {
                        CalendarDayItem(weekDay = it.name.substring(0, 1), monthDay = "1")
                    }
                }
                Text(
                    modifier = Modifier.padding(vertical = spacing.spaceSmall),
                    text = "Today",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.agendaItems) { agendaItems ->
                        with(agendaItems) {
                            CardItem2(
                                title = when (this) {
                                    is TaskModel -> {
                                        String.format(Locale.getDefault(), "Task: %s", title)
                                    }

                                    else -> title
                                },
                                description = description,
                                date = date.toString(),
                                isDone = when (this) {
                                    is TaskModel -> {
                                        isDone
                                    }

                                    else -> false
                                },
                                isDropDownMenuShown =
                                state.agendaItemWithOpenedOptions != null && state.agendaItemWithOpenedOptions.id == id,
                                onDismissDropDownMenu = {
                                    onAction(AgendaAction.OnDismissedOptionsDropdown)
                                },
                                primaryColor = when (this) {
                                    is EventModel -> {
                                        MaterialTheme.colorScheme.primary
                                    }

                                    is ReminderModel -> {
                                        MaterialTheme.colorScheme.inversePrimary
                                    }

                                    is TaskModel -> {
                                        MaterialTheme.colorScheme.tertiary
                                    }
                                },
                                onCardClick = {
                                    onAction(AgendaAction.OnAgendaItemClicked(agendaModel = this))
                                },
                                onBulletClick = {
                                    if (this is TaskModel) {
                                        onAction(AgendaAction.OnDoneBulletClicked(taskModel = this))
                                    }
                                },
                                onOptionsClick = {
                                    onAction(AgendaAction.OnAgendaItemOptionsClicked(this))
                                },
                                onOpenOptionClick = {
                                    onAction(AgendaAction.OnOpenAgendaItemClicked(agendaModel = this))
                                    onAction(AgendaAction.OnDismissedOptionsDropdown)
                                },
                                onEditOptionClick = {
                                    onAction(AgendaAction.OnEditAgendaItemClicked(agendaModel = this))
                                    onAction(AgendaAction.OnDismissedOptionsDropdown)
                                },
                                onDeleteOptionClick = {
                                    onAction(AgendaAction.OnDeleteAgendaItemClicked(agendaModel = this))
                                    onAction(AgendaAction.OnDismissedOptionsDropdown)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AgendaScreenPreview() {
    TaskyAppTheme {
        AgendaScreen(
            state = AgendaState(),
            onAction = {}
        )
    }
}
