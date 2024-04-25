package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import com.davidtomas.taskyapp.features.auth.presentation._common.navigation.AuthRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

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
