package com.davidtomas.taskyapp.features.agenda.data.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.navigation.MainActivity

object TaskyNotificationService {

    const val TASKY_CHANNEL_ID = "tasky_channel"

    fun showNotification(
        context: Context,
        notificationId: Int,
        agendaId: String,
        title: String,
        description: String,
        agendaType: String,
    ) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent =
            Intent(
                Intent.ACTION_VIEW,
                baseUriGenerator(agendaType, agendaId).toUri(),
                context,
                MainActivity::class.java
            )

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                notificationId,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        val notification =
            NotificationCompat.Builder(context, TASKY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun baseUriGenerator(agendaType: String, agendaId: String): String {
        return "tasky://detail/$agendaType/${ScreenMode.REVIEW.name}/$agendaId"
    }
}