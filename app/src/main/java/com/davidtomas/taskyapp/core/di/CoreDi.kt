package com.davidtomas.taskyapp.core.di

import com.davidtomas.taskyapp.features.auth.data.local.TokenManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    dataModule()
}

private fun Module.dataModule() {
    singleOf(::TokenManager)
}

