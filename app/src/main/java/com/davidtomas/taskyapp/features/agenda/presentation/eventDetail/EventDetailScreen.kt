package com.davidtomas.taskyapp.features.agenda.presentation.eventDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components.AgendaTypeComposable
import com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components.AttendeeItem
import com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components.DescriptionComposable
import com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components.NotificationTimePicker
import com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components.PillsSelector
import com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components.TimeDatePicker
import com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components.TitleComposable

@Composable
fun EventDetailScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Header(
            modifier = Modifier,
            leadingComposable = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "",
                    tint = Color.White
                )
            },
            trailingComposable = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_action),
                    contentDescription = "",
                    tint = Color.White

                )
            }
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .weight(1f)
                .padding(
                    vertical = 30.dp,
                    horizontal = 16.dp
                )
        ) {
            AgendaTypeComposable(
                title = "Event",
                rectangleColor = Color.Green
            )
            Spacer(modifier = Modifier.height(33.dp))
            TitleComposable(
                title = "Meeting",
                onNavigateToEditClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(17.dp))
            DescriptionComposable(
                description = "Esto es una prueba de una descripción. Quiero hacerla más menos larga",
                onNavigateToEditClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(17.dp))
            TimeDatePicker(
                label = "From",
                hour = "08:00",
                date = "Jul 21 2022",
                onChangeHourIconClick = { /*TODO*/ },
                onChangeDateIconClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(17.dp))
            TimeDatePicker(
                label = "To",
                hour = "08:00",
                date = "Jul 21 2022",
                onChangeHourIconClick = { /*TODO*/ },
                onChangeDateIconClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(17.dp))
            NotificationTimePicker(
                text = "30 minutes before",
                onNavigateToEditClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(17.dp))
            Text(text = "Visitors")
            Spacer(modifier = Modifier.height(17.dp))
            PillsSelector(
                modifier = Modifier.fillMaxWidth(),
                onPillClicked = {}
            )
            Spacer(modifier = Modifier.height(17.dp))
            Text(text = "Going")
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                AttendeeItem(fullName = "Ann Allen", onDeleteIconClicked = { /*TODO*/ })
                AttendeeItem(fullName = "Wade Warren", onDeleteIconClicked = { /*TODO*/ })
                AttendeeItem(
                    fullName = "Esther Howard",
                    onDeleteIconClicked = { /*TODO*/ }
                )
            }
            Spacer(modifier = Modifier.height(17.dp))
            Text(text = "Not Going")
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                AttendeeItem(fullName = "Ann Allen", onDeleteIconClicked = { /*TODO*/ })
                AttendeeItem(fullName = "Wade Warren", onDeleteIconClicked = { /*TODO*/ })
                AttendeeItem(
                    fullName = "Esther Howard",
                    onDeleteIconClicked = { /*TODO*/ }
                )
            }
            Spacer(modifier = Modifier.height(17.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Not Going"
            )
        }
    }
}

@Preview
@Composable
fun EventDetailScreenPreview() {
    TaskyAppTheme {
        EventDetailScreen()
    }
}
