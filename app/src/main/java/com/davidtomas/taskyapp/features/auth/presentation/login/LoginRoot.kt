package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.davidtomas.taskyapp.core.navigation.Route

@Composable
fun LoginRoot(
    navController: NavController,
    // viewModel: LoginViewModel = koinViewModel()
) {
    LoginScreen(
        navigateToAgenda = {
            navController.navigate(Route.AGENDA) {
                popUpTo(Route.AUTH) {
                    inclusive = true
                }
            }
        },
        navigateToRegister = {
            navController.navigate(Route.REGISTER)
        }
    )
}
