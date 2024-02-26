package com.davidtomas.taskyapp.core.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davidtomas.taskyapp.features.agenda.presentation.AgendaScreen
import com.davidtomas.taskyapp.features.auth.login.presentation.LoginScreen
import com.davidtomas.taskyapp.features.auth.register.presentation.RegisterScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun TaskyNavHost(isAuthenticated: Boolean) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = if (isAuthenticated) Route.AGENDA else Route.LOGIN
            ) {
                composable(route = Route.LOGIN) {
                    LoginScreen()
                }
                composable(route = Route.REGISTER) {
                    RegisterScreen()
                }
                composable(route = Route.AGENDA) {
                    AgendaScreen()
                }
            }
        }
    )
}
