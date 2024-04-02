package com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING

data class EditTitleOrDescriptionState(
    val text: String = String.EMPTY_STRING,
    val editType: EditType = EditType.TITLE
)
