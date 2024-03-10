package com.davidtomas.taskyapp.core.di

import com.davidtomas.taskyapp.MainActivityViewModel
import com.davidtomas.taskyapp.core.domain.useCase.AuthenticateUseCase
import com.davidtomas.taskyapp.features.auth.data.local.TokenManager
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    dataModule()
    domainModule()
    presentation()
}

private fun Module.dataModule() {
    singleOf(::TokenManager)
}

private fun Module.domainModule() {
    singleOf(::AuthenticateUseCase)
}

private fun Module.presentation() {
    viewModelOf(::MainActivityViewModel)
}