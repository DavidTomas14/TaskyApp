package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import com.davidtomas.taskyapp.features.auth.presentation._common.navigation.AuthRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }

                is LoginUiEvent.Navigate -> navController.navigate(AgendaRoutes.AGENDA) {
                    popUpTo(AuthRoutes.AUTH_GRAPH) {
                        inclusive = true
                    }
                }
            }
        }
    }
    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is LoginAction.NavigateToRegisterButtonClick -> {
                    navController.navigate(AuthRoutes.REGISTER)
                }

                else -> viewModel.onAction(action)
            }
        }
    )
}
