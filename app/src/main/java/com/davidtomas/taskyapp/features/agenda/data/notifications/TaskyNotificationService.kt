package com.davidtomas.taskyapp.features.agenda.data.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes

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

        val uri =
            Uri.parse(
                baseUriGenerator(agendaType, agendaId)
            )
        val intent =
            Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage(context.packageName)
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val notification =
            NotificationCompat.Builder(context, TASKY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.tasky_logo)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(notificationId, notification)
    }

    private fun baseUriGenerator(agendaType: String, agendaId: String): String {
        return "${AgendaRoutes.AGENDA_DETAIL}/${agendaType}/${ScreenMode.REVIEW.name}/${agendaId}"
    }
}