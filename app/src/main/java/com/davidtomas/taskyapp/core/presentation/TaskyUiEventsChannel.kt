package com.davidtomas.taskyapp.core.presentation

import com.davidtomas.taskyapp.core.presentation.util.UiEventSender
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object TaskyUiEventsChannel : UiEventSender {
    private val _taskyUiEvent = Channel<TaskyUiEvent>()
    val taskyUiEvent = _taskyUiEvent.receiveAsFlow()

    override suspend fun sendEvent(event: TaskyUiEvent) {
        _taskyUiEvent.send(event)
    }
}