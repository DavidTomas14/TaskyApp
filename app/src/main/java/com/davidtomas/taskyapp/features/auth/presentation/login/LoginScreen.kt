package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    navigateToAgenda: () -> Unit,
    navigateToRegister: () -> Unit,
) {
    Column {
        Text(text = "Login Screen")
        Button(onClick = { navigateToAgenda() }) {
            Text(text = "Navigate To Agenda")
        }
        Button(onClick = { navigateToRegister() }) {
            Text(text = "Navigate To Register")
        }
    }
}
