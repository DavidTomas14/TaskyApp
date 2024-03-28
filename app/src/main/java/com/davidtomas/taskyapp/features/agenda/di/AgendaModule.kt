package com.davidtomas.taskyapp.features.agenda.di

import com.davidtomas.taskyapp.features.agenda.data._common.local.TaskyRealmDB
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaService
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.agenda.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.domain.useCase.GetAgendaUseCase
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.AgendaViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaModule = module {
    dataModule()
    domainModule()
    presentationModule()
}

private fun Module.dataModule() {
    single { TaskyRealmDB.create() }
    singleOf(::AgendaRepository)
    singleOf(::AgendaServiceImpl) bind AgendaService::class
    singleOf(::TaskLocalSourceImpl) bind TaskLocalSource::class
    singleOf(::EventLocalSourceImpl) bind EventLocalSource::class
    singleOf(::ReminderLocalSourceImpl) bind ReminderLocalSource::class
}

private fun Module.domainModule() {
    singleOf(::GetAgendaUseCase)
}

private fun Module.presentationModule() {
    singleOf(::AgendaViewModel)
}