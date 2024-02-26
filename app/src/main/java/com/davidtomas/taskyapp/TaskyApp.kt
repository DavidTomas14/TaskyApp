package com.davidtomas.taskyapp

import android.app.Application
import com.davidtomas.taskyapp.features.agenda.di.agendaModule
import com.davidtomas.taskyapp.features.auth.common.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TaskyApp)
            modules(agendaModule, authModule)
        }
    }
}
