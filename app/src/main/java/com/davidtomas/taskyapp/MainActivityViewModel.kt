package com.davidtomas.taskyapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    init {

        viewModelScope.launch {
            delay(ESTIMATED_TIME)
            _isAuthenticated.value = true
        }
    }

    companion object {
        const val ESTIMATED_TIME = 3000L
    }
}
