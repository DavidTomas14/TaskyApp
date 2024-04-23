package com.davidtomas.taskyapp.features.agenda.data.notifications

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class NotificationSchedulerImpl(private val context: Context) : NotificationScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduleNotification(agendaItem: AgendaModel) {
        alarmManager.apply {
            setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                agendaItem.remindAt,
                createPendingIntent(agendaItem),
            )
        }
    }

    override fun cancelScheduledNotificationAndPendingIntent(agendaItem: AgendaModel) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(agendaItem.id.hashCode())
        createPendingIntent(agendaItem).cancel()
    }

    private fun createPendingIntent(agendaItem: AgendaModel): PendingIntent {
        val notificationID = agendaItem.id.hashCode()
        val intent =
            Intent(context, NotificationBroadcastReceiver::class.java)
                .apply {
                    putExtra("AGENDA_TYPE", agendaItem.agendaType.name)
                    putExtra("NOTIFICATION_ID", notificationID)
                    putExtra("AGENDA_ID", agendaItem.id)
                    putExtra("TITLE", agendaItem.title)
                    putExtra(
                        "DESCRIPTION",
                        ZonedDateTime.ofInstant(
                            Instant.ofEpochMilli(agendaItem.remindAt),
                            ZoneId.systemDefault()
                        ).toString()
                    )
                }
        return PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }
}