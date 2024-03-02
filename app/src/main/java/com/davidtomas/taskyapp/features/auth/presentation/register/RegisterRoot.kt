package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.davidtomas.taskyapp.core.presentation.navigation.Route

@Composable
fun RegisterRoot(
    navController: NavController,
    // viewModel: RegisterViewModel = koinViewModel()
) {
    RegisterScreen {
        navController.navigate(Route.LOGIN)
    }
}
