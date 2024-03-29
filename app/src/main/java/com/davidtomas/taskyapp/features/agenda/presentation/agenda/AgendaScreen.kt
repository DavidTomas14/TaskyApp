package com.davidtomas.taskyapp.features.agenda.presentation.agenda

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.CardItem2
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.CalendarDayItem
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.InitialsIcon
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.MonthSelector
import java.time.DayOfWeek

@Composable
fun AgendaScreen(
    state: AgendaState,
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
                items(state.agendaItems) { agendaItem ->
                    with(agendaItem) {
                        when (this) {
                            is EventModel -> {
                                CardItem2(
                                    title = title,
                                    description = description,
                                    date = date.toString(),
                                    isChecked = true,
                                    onCardClick = { /*TODO*/ },
                                    onBulletClick = { /*TODO*/ },
                                    onOptionsClick = { /*TODO*/ })
                            }

                            is ReminderModel -> {
                                CardItem2(
                                    title = title,
                                    description = description,
                                    date = date.toString(),
                                    isChecked = false,
                                    primaryColor = MaterialTheme.colorScheme.tertiary,
                                    onCardClick = { /*TODO*/ },
                                    onBulletClick = { /*TODO*/ },
                                    onOptionsClick = { /*TODO*/ }
                                )
                            }

                            is TaskModel -> {
                                CardItem2(
                                    title = title,
                                    description = description,
                                    date = date.toString(),
                                    isChecked = false,
                                    primaryColor = MaterialTheme.colorScheme.inversePrimary,
                                    onCardClick = { /*TODO*/ },
                                    onBulletClick = { /*TODO*/ },
                                    onOptionsClick = { /*TODO*/ }
                                )
                            }
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
        )
    }
}
