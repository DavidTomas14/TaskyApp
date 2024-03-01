package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.davidtomas.taskyapp.core.presentation.navigation.Route
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is NavigateToRegister -> {
                    navController.navigate(Route.REGISTER)
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}
