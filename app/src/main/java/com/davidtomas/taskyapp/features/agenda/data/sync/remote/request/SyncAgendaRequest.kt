package com.davidtomas.taskyapp.features.agenda.data.sync.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class SyncAgendaRequest(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>,
)
