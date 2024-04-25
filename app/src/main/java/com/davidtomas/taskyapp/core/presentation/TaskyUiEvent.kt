package com.davidtomas.taskyapp.core.presentation

import com.davidtomas.taskyapp.core.presentation.util.UiText

sealed interface TaskyUiEvent {
    data class ShowSnackBar(val message: UiText) : TaskyUiEvent
}