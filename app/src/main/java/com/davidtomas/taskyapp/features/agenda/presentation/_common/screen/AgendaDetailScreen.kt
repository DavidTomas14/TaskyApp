package com.davidtomas.taskyapp.features.agenda.presentation._common.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.core.presentation.util.formatToDayHourOrMinutes
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.AgendaType
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.AttendeesSection
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.Description
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.NotificationTimePicker
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.Photos
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.TimeDatePicker
import com.davidtomas.taskyapp.features.agenda.presentation._common.components.Title
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.AgendaDetailAction
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.AgendaDetailState

@Composable
fun AgendaDetailScreen(
    state: AgendaDetailState,
    onAction: (AgendaDetailAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
            .verticalScroll(rememberScrollState())
    ) {
        Header(
            modifier = Modifier,
            leadingComposable = {
                Icon(
                    modifier = Modifier
                        .clickable { onAction(AgendaDetailAction.OnCloseDetailIconClick) },
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "",
                    tint = Color.White
                )
            },
            trailingComposable = {
                when (state.screenMode) {
                    ScreenMode.REVIEW -> {
                        Icon(
                            modifier = Modifier
                                .clickable { onAction(AgendaDetailAction.OnEditIconClick) },
                            painter = painterResource(id = R.drawable.ic_edit_action),
                            contentDescription = "",
                            tint = Color.White

                        )
                    }

                    else -> {
                        Text(
                            modifier = Modifier
                                .clickable { onAction(AgendaDetailAction.OnSaveClick) },
                            text = "Save",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(
                    vertical = 30.dp,
                    horizontal = 16.dp
                )
                .then(if (state.agendaType != AgendaType.EVENT) Modifier.weight(1f) else Modifier)
        ) {
            AgendaType(agendaType = state.agendaType)
            Spacer(modifier = Modifier.height(33.dp))
            Title(
                title = state.title,
                onNavigateToEditClick = { onAction(AgendaDetailAction.OnNavigateToEditTitleClick) },
                isEditable = state.screenMode != ScreenMode.REVIEW
            )
            Spacer(modifier = Modifier.height(17.dp))
            Description(
                description = state.description,
                onNavigateToEditClick = { onAction(AgendaDetailAction.OnNavigateToEditDescriptionClick) },
                isEditable = state.screenMode != ScreenMode.REVIEW
            )

            if (state.agendaType == AgendaType.EVENT) {
                Photos(
                    photos = state.photos ?: emptyList(),
                    onPhotoClicked = {
                        onAction(AgendaDetailAction.OnPhotoClicked(it))
                    },
                    onAddedPhoto = {
                        onAction(AgendaDetailAction.OnAddedPhoto(it))
                    },
                    isEditable = state.screenMode != ScreenMode.REVIEW
                )
            }
            Spacer(modifier = Modifier.height(17.dp))
            TimeDatePicker(
                zonedDateTime = state.date,
                label = when (state.agendaType) {
                    AgendaType.EVENT -> "From"
                    else -> "At"
                },
                isEditable = state.screenMode != ScreenMode.REVIEW,
                onConfirmChangedDateClick = { millisOfDate ->
                    onAction(AgendaDetailAction.OnDateChanged(millisOfDate))
                },
                onConfirmChangedTimeClick = { millisOfHour, millisOfMinutes ->
                    onAction(
                        AgendaDetailAction.OnHourMinutesChanged(millisOfHour, millisOfMinutes)
                    )
                },
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (state.agendaType == AgendaType.EVENT)
                TimeDatePicker(
                    zonedDateTime = state.date,
                    label = "To",
                    isEditable = state.screenMode != ScreenMode.REVIEW,
                    onConfirmChangedDateClick = { millisOfDate ->
                        onAction(AgendaDetailAction.OnDateChanged(millisOfDate))
                    },
                    onConfirmChangedTimeClick = { millisOfHour, millisOfMinutes ->
                        onAction(
                            AgendaDetailAction.OnHourMinutesChanged(
                                millisOfHour, millisOfMinutes
                            )
                        )
                    },
                )
            Spacer(modifier = Modifier.height(17.dp))
            NotificationTimePicker(
                text = state.remindIn.formatToDayHourOrMinutes(),
                onOptionSelected = { notificationMillis ->
                    onAction(AgendaDetailAction.OnNotificationOptionSelected(notificationMillis))
                },
                isEditable = state.screenMode != ScreenMode.REVIEW
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (state.agendaType == AgendaType.EVENT) {
                AttendeesSection(
                    attendeeList = listOf(
                        AttendeeModel(
                            email = "test@test.com",
                            fullName = "David Tomas",
                            eventId = "1234",
                            isGoing = true,
                            remindAt = 0L
                        ),
                        AttendeeModel(
                            email = "test@test.com",
                            fullName = "David Tomas",
                            eventId = "1234",
                            isGoing = false,
                            remindAt = 0L
                        ),
                        AttendeeModel(
                            email = "test@test.com",
                            fullName = "David Tomas",
                            eventId = "1234",
                            isGoing = true,
                            remindAt = 0L
                        ),
                        AttendeeModel(
                            email = "test@test.com",
                            fullName = "David Tomas",
                            eventId = "1234",
                            isGoing = false,
                            remindAt = 0L
                        )
                    ),
                    onDeleteAttendeeIconClick = {},
                    isEditable = state.screenMode != ScreenMode.REVIEW,
                )
            }
            if (state.screenMode == ScreenMode.REVIEW) {
                Box(
                    modifier = if (state.agendaType != AgendaType.EVENT) Modifier.weight(1f) else Modifier,
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            onAction(AgendaDetailAction.OnDeleteButtonClick)
                        }
                    ) {
                        Text(text = "Delete Item")
                    }
                }
            }
        }
    }
}

class PreviewParameters(
    val agendaType: AgendaType,
    val screenMode: ScreenMode
)

class AgendaTypeProvider : PreviewParameterProvider<PreviewParameters> {
    override val values: Sequence<PreviewParameters>
        get() = sequenceOf(
            PreviewParameters(
                agendaType = AgendaType.REMINDER,
                screenMode = ScreenMode.EDIT_ADD
            ),
            PreviewParameters(
                agendaType = AgendaType.REMINDER,
                screenMode = ScreenMode.REVIEW
            ),
            PreviewParameters(
                agendaType = AgendaType.TASK,
                screenMode = ScreenMode.EDIT_ADD
            ),
            PreviewParameters(
                agendaType = AgendaType.TASK,
                screenMode = ScreenMode.REVIEW
            ),
            PreviewParameters(
                agendaType = AgendaType.EVENT,
                screenMode = ScreenMode.EDIT_ADD
            ),
            PreviewParameters(
                agendaType = AgendaType.EVENT,
                screenMode = ScreenMode.REVIEW
            ),
        )
}

@Preview()
@Composable
fun AgendaDetailScreenPreview(
    @PreviewParameter(AgendaTypeProvider::class) previewParameters: PreviewParameters,
) {
    TaskyAppTheme {
        Column {
            AgendaDetailScreen(
                state = AgendaDetailState().copy(
                    agendaType = previewParameters.agendaType,
                    screenMode = previewParameters.screenMode
                ),
                onAction = {}
            )
        }
    }
}
