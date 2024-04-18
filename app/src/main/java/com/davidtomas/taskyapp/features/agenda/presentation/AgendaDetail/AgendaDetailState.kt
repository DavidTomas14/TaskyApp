package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.Immutable
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.core.presentation.util.UiText
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import java.time.ZonedDateTime
@Immutable
data class AgendaDetailState(
    val title: String = String.EMPTY_STRING,
    val description: String = String.EMPTY_STRING,
    val fromDate: ZonedDateTime = ZonedDateTime.now(),
    val remindIn: Long = (30 * 60 * 1000L),
    val screenMode: ScreenMode = ScreenMode.REVIEW,
    val agendaType: AgendaType = AgendaType.REMINDER,
    val photos: List<PhotoModel> = emptyList(),
    val attendees: List<AttendeeModel>? = null,
    val addingVisitorEmail: String = String.EMPTY_STRING,
    val addingVisitorEmailErrMsg: UiText? = null,
    val isEmailChecked: Boolean = false,
    val isAddVisitorDialogShown: Boolean = false,
    val toDate: ZonedDateTime = ZonedDateTime.now(),
    val host: String? = null
)
