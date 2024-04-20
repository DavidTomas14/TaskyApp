package com.davidtomas.taskyapp.features.agenda.data.notifications

import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel

interface NotificationScheduler {

    fun scheduleNotification(agendaItem: AgendaModel)
    fun cancelScheduledNotificationAndPendingIntent(agendaItem: AgendaModel)
}