package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRoot(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is RegisterUiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    navController.popBackStack()
                }
            }
        }
    }
    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is RegisterAction.NavigateBackToLogin -> {
                    navController.popBackStack()
                }

                else -> {
                    viewModel.onAction(action = action)
                }
            }
        }
    )
}