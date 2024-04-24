package com.davidtomas.taskyapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.davidtomas.taskyapp.core.di.coreModule
import com.davidtomas.taskyapp.features.agenda.data.notifications.TaskyNotificationService
import com.davidtomas.taskyapp.features.agenda.di.agendaModule
import com.davidtomas.taskyapp.features.auth.di.authModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskyApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TaskyApp)
            modules(coreModule, agendaModule, authModule)
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            TaskyNotificationService.TASKY_CHANNEL_ID,
            "Tasky Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used for sending reminders"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
