package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.core.presentation.util.formatToMMM
import com.davidtomas.taskyapp.core.presentation.util.formatToMMMdHHmm
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.DropDownItems
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.DropDownOptions
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.AddItemFabWithDropdown
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.CalendarDayItem
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.CardItem2
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.InitialsIcon
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.MonthSelector
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AgendaScreen(
    state: AgendaState,
    onAction: (AgendaAction) -> Unit
) {
    var showLogoutDropDown by remember {
        mutableStateOf(false)
    }
    Scaffold(
        floatingActionButton = {
            AddItemFabWithDropdown {
                onAction(AgendaAction.OnAddAgendaItemOptionClicked(it))
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
                        month = state.dateSelected.formatToMMM(),
                        onDateSelected = {
                            onAction(AgendaAction.OnDateMonthPickerSelected(it))
                        }
                    )
                },
                trailingComposable = {
                    Box() {
                        InitialsIcon(
                            initials = "AB",
                            onIconClick = {
                                showLogoutDropDown = true
                            }
                        )
                        DropDownOptions(
                            isShown = showLogoutDropDown,
                            onDismissRequest = { showLogoutDropDown = false },
                            dropDownItems = listOf(
                                DropDownItems(
                                    text = "Logout",
                                    leadingIcon = R.drawable.ic_logout,
                                    onClick = {
                                        onAction(AgendaAction.OnInitialsIconClicked)
                                        showLogoutDropDown = false
                                    }
                                )
                            )
                        )
                    }
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
                    state.weekShown.forEach { (dayOfWeek, date) ->
                        CalendarDayItem(
                            weekDay = dayOfWeek.name.substring(0, 1),
                            monthDay = date.dayOfMonth.toString(),
                            isDaySelected = date.dayOfMonth == state.dateSelected.dayOfMonth &&
                                date.monthValue == state.dateSelected.monthValue,
                            onDateSelected = {
                                onAction(AgendaAction.OnDayClicked(date))
                            }
                        )
                    }
                }
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
                                fromDate = date.formatToMMMdHHmm(),
                                toDate = if (this is EventModel) toDate.formatToMMMdHHmm() else null,
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
