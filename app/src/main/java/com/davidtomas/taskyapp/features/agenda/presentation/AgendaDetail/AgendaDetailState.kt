package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription.EditType
import java.time.ZonedDateTime

data class AgendaDetailState(
    val title: String = String.EMPTY_STRING,
    val description: String = String.EMPTY_STRING,
    val date: ZonedDateTime = ZonedDateTime.now(),
    val remindAt: Long = (30 * 60 * 1000L),
    val screenMode: ScreenMode = ScreenMode.REVIEW,
    val agendaType: AgendaType = AgendaType.REMINDER,
    val showNotificationDropdown: Boolean = false,
    val editType: EditType = EditType.TITLE,
)
