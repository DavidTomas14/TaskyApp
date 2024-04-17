package com.davidtomas.taskyapp.features.agenda.data._common.remote

object AgendaPaths {
    const val GET_FULL_AGENDA_ROUTE = "/fullAgenda"
    const val SYNC_AGENDA_ROUTE = "/syncAgenda"
    const val CHECK_ATTENDEE_ROUTE = "/attendee"
    const val EVENT_ROUTE = "/event"
    const val REMINDER_ROUTE = "/reminder"
    const val TASK_ROUTE = "/task"

    // PARAMS
    const val EMAIL_PARAM = "email"
    const val TASK_ID_PARAM = "taskId"
    const val REMINDER_ID_PARAM = "reminderId"
    const val EVENT_ID_PARAM = "eventId"
}