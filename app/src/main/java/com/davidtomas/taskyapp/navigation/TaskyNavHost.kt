package com.davidtomas.taskyapp.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.AgendaRoot
import com.davidtomas.taskyapp.features.auth.presentation.login.LoginRoot
import com.davidtomas.taskyapp.features.auth.presentation.register.RegisterRoot

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
                startDestination = Route.AUTH
            ) {
                navigation(
                    startDestination = if (isAuthenticated) Route.AGENDA else Route.LOGIN,
                    route = Route.AUTH
                ) {
                    composable(route = Route.LOGIN) {
                        LoginRoot(
                            snackbarHostState = snackbarHostState,
                            navController = navController
                        )
                    }
                    composable(route = Route.REGISTER) {
                        RegisterRoot(
                            snackbarHostState = snackbarHostState,
                            navController = navController
                        )
                    }
                    composable(route = Route.AGENDA) {
                        AgendaRoot()
                    }
                }
            }
        }
    )
}
