package com.davidtomas.taskyapp.core.presentation.util

import com.davidtomas.taskyapp.core.presentation.TaskyUiEvent

interface UiEventSender {
    suspend fun sendEvent(event: TaskyUiEvent)
}