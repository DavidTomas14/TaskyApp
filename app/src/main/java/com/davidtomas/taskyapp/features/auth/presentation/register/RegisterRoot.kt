package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is NavigateBackToLogin -> {
                    navController.popBackStack()
                }

                else -> {
                    viewModel.onAction(action = action)
                }
            }
        }
    )
}