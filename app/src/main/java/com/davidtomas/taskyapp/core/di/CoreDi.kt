package com.davidtomas.taskyapp.core.di

import com.davidtomas.taskyapp.MainActivityViewModel
import com.davidtomas.taskyapp.core.domain.useCase.AuthenticateUseCase
import com.davidtomas.taskyapp.features.auth.data.local.TokenDataStore
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    domainModule()
    presentation()
}

private fun Module.dataModule() {
    singleOf(::TokenDataStore)
}

private fun Module.domainModule() {
    singleOf(::AuthenticateUseCase)
}

private fun Module.presentation() {
    viewModelOf(::MainActivityViewModel)
}