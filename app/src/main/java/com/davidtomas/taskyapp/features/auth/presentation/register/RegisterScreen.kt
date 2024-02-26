package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit
) {
    Column {
        Text(text = "Login")
        Button(onClick = { navigateToLogin() }) {
            Text(text = "navigate to Login")
        }
    }
}
