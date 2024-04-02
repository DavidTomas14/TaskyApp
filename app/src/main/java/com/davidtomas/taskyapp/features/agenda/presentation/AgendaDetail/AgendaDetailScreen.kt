package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.AgendaType
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.AttendeesSection
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.Description
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.NotificationTimePicker
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.Photos
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.TimeDatePicker
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components.Title

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgendaDetailScreen(
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
            AgendaType(agendaType = agendaType)
            Spacer(modifier = Modifier.height(33.dp))
            Title(
                title = "Meeting",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            Description(
                description = "Esto es una prueba de una descripción. Quiero hacerla más menos larga",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (agendaType == AgendaType.EVENT) {
                Photos(
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
                onConfirmChangedDateClick = { },
                onConfirmChangedTimeClick = {},
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (agendaType == AgendaType.EVENT)
                TimeDatePicker(
                    label = "To",
                    onConfirmChangedDateClick = {},
                    onConfirmChangedTimeClick = {},
                    isEditable = isEditable
                )
            Spacer(modifier = Modifier.height(17.dp))
            NotificationTimePicker(
                text = "30 minutes before",
                onNavigateToEditClick = { /*TODO*/ },
                isEditable = isEditable
            )
            Spacer(modifier = Modifier.height(17.dp))
            if (agendaType == AgendaType.EVENT) {
                AttendeesSection(isEditable)
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun AgendaDetailScreenPreview(
    @PreviewParameter(AgendaTypeProvider::class) previewParameters: PreviewParameters,
) {
    TaskyAppTheme {
        Column {
            AgendaDetailScreen(
                agendaType = previewParameters.agendaType,
                isEditable = previewParameters.isEditable
            )
        }
    }
}
