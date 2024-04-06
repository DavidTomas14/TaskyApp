package com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.features.agenda.domain.model.EditType

data class EditTextState(
    val text: String = String.EMPTY_STRING,
    val editType: EditType = EditType.TITLE
)
