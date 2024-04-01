package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.AgendaTypeComposable
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.AttendeeItem
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.DescriptionComposable
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.NotificationTimePicker
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.PhotosComposable
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.PillsSelector
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.TimeDatePicker
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.TitleComposable

@Composable
fun EventDetailScreen(
    agendaType: AgendaType,
    isEditable: Boolean
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
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "",
                    tint = Color.White
                )
            },
            trailingComposable = {
                if (isEditable)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit_action),
                        contentDescription = "",
                        tint = Color.White

                    )
                else
                    Text(
                        text = "Save",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
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
                .then(if (agendaType != AgendaType.EVENT) Modifier.weight(1f) else Modifier)
        ) {
            AgendaTypeComposable(agendaType = agendaType)
            Spacer(modifier = Modifier.height(33.dp))
            TitleComposable(
                title = "Meeting",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            DescriptionComposable(
                description = "Esto es una prueba de una descripción. Quiero hacerla más menos larga",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (agendaType == AgendaType.EVENT) {
                PhotosComposable(
                    photos = listOf(),
                    onAddedPhoto = {},
                )
                Spacer(modifier = Modifier.height(17.dp))
            }
            Spacer(modifier = Modifier.height(17.dp))
            TimeDatePicker(
                label = when (agendaType) {
                    AgendaType.EVENT -> "From"
                    AgendaType.TASK -> "At"
                    AgendaType.REMINDER -> "At"
                },
                hour = "08:00",
                date = "Jul 21 2022",
                onChangeHourIconClick = { /*TODO*/ },
                onChangeDateIconClick = { /*TODO*/ },
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (agendaType == AgendaType.EVENT)
                TimeDatePicker(
                    label = "To",
                    hour = "08:00",
                    date = "Jul 21 2022",
                    onChangeHourIconClick = { /*TODO*/ },
                    onChangeDateIconClick = { /*TODO*/ },
                    isEditable
                )
            Spacer(modifier = Modifier.height(17.dp))
            NotificationTimePicker(
                text = "30 minutes before",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (agendaType == AgendaType.EVENT) {
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
            Box(
                modifier = if (agendaType != AgendaType.EVENT) Modifier.weight(1f) else Modifier,
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Delete Item")
                }
            }
        }
    }
}

class PreviewParameters(
    val agendaType: AgendaType,
    val isEditable: Boolean
)

class AgendaTypeProvider : PreviewParameterProvider<PreviewParameters> {
    override val values: Sequence<PreviewParameters>
        get() = sequenceOf(
            PreviewParameters(
                agendaType = AgendaType.REMINDER,
                isEditable = false
            ),
            PreviewParameters(
                agendaType = AgendaType.REMINDER,
                isEditable = true
            ),
            PreviewParameters(
                agendaType = AgendaType.TASK,
                isEditable = false
            ),
            PreviewParameters(
                agendaType = AgendaType.TASK,
                isEditable = true
            ),
            PreviewParameters(
                agendaType = AgendaType.EVENT,
                isEditable = false
            ),
            PreviewParameters(
                agendaType = AgendaType.EVENT,
                isEditable = true
            ),
        )
}

@Preview()
@Composable
fun EventDetailScreenPreview(
    @PreviewParameter(AgendaTypeProvider::class) previewParameters: PreviewParameters,
) {
    TaskyAppTheme {
        Column {
            EventDetailScreen(
                agendaType = previewParameters.agendaType,
                isEditable = previewParameters.isEditable
            )
        }
    }
}
