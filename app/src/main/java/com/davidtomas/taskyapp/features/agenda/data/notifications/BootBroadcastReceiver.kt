package com.davidtomas.taskyapp.features.agenda.data.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootBroadcastReceiver :
    BroadcastReceiver(), KoinComponent {

    private val notificationScheduler: NotificationScheduler by inject()
    private val agendaRepository: AgendaRepository by inject()
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("Boot", "Hello Im booted")
            // Call method to re-schedule your notifications
            val hasNotificationPermission =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) == PackageManager.PERMISSION_GRANTED
                } else {
                    true
                }

            Log.d("Boot", hasNotificationPermission.toString())
            if (hasNotificationPermission) {
                CoroutineScope(Dispatchers.IO).launch {
                    agendaRepository.getFutureAgendaItems()
                        .forEach { agendaItem ->
                            Log.d("Boot", agendaItem.toString())
                            notificationScheduler.scheduleNotification(agendaItem)
                        }
                }
            }
        }
    }
}