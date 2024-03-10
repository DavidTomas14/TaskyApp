package com.davidtomas.taskyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.core.domain.useCase.AuthenticateUseCase
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
        }
    }
}
