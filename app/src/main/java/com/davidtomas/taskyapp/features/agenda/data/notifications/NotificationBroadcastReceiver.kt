package com.davidtomas.taskyapp.features.agenda.data.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import org.koin.core.component.KoinComponent

class NotificationBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    override fun onReceive(context: Context, intent: Intent) {
            // Retrieve details from Intent and show notification
            val notificationId = intent.getIntExtra("NOTIFICATION_ID", 0)
            val agendaType = intent.getStringExtra("AGENDA_TYPE") ?: ""
            val agendaId = intent.getStringExtra("AGENDA_ID") ?: ""
            val title = intent.getStringExtra("TITLE") ?: "Reminder"
            val description = intent.getStringExtra("DESCRIPTION") ?: "You have a reminder"

            val hasNotificationPermission =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) == PackageManager.PERMISSION_GRANTED
                } else {
                    true
                }

            if (hasNotificationPermission) {
                TaskyNotificationService.showNotification(
                    context = context,
                    notificationId = notificationId,
                    title = title,
                    description = description,
                    agendaId = agendaId,
                    agendaType = agendaType,
                )
        }
    }
}