package com.davidtomas.taskyapp.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.features.auth.domain.useCase.AuthenticateUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val authenticateUseCase: AuthenticateUseCase
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    private val _isAuthChecked = MutableStateFlow(false)
    val isAuthChecked = _isAuthChecked.asStateFlow()

    private val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished = _isSplashFinished.asStateFlow()

    init {
        viewModelScope.launch {
            authenticate()
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            authenticateUseCase().fold(
                onError = {
                    this@MainActivityViewModel._isAuthenticated.value = false
                },
                onSuccess = {
                    _isAuthenticated.value = true
                }
            )
            _isAuthChecked.value = true
            // Added delay to make the splash take time necessary to authenticate
            delay(2500L)
            _isSplashFinished.value = true
        }
    }
}
