package com.davidtomas.taskyapp.core.di

import com.davidtomas.taskyapp.features.auth.data.local.TaskyDataStore
import com.davidtomas.taskyapp.features.auth.data.local.TaskyDataStoreImpl
import com.davidtomas.taskyapp.features.auth.domain.useCase.AuthenticateUseCase
import com.davidtomas.taskyapp.navigation.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    dataModule()
    domainModule()
    presentation()
}

private fun Module.dataModule() {
    singleOf(::TaskyDataStoreImpl) bind TaskyDataStore::class
}

private fun Module.domainModule() {
    singleOf(::AuthenticateUseCase)
}

private fun Module.presentation() {
    viewModelOf(::MainActivityViewModel)
}