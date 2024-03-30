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
import com.davidtomas.taskyapp.features.agenda.presentation.EmptyScreen
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.AgendaRoot
import com.davidtomas.taskyapp.features.auth.presentation._common.navigation.AuthRoutes
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
                startDestination = AuthRoutes.AUTH
            ) {
                navigation(
                    startDestination = if (isAuthenticated) AgendaRoutes.AGENDA else AuthRoutes.LOGIN,
                    route = AuthRoutes.AUTH
                ) {
                    composable(route = AuthRoutes.LOGIN) {
                        LoginRoot(
                            snackbarHostState = snackbarHostState,
                            navController = navController
                        )
                    }
                    composable(route = AuthRoutes.REGISTER) {
                        RegisterRoot(
                            snackbarHostState = snackbarHostState,
                            navController = navController
                        )
                    }
                    composable(route = AgendaRoutes.AGENDA) {
                        AgendaRoot(
                            navController = navController
                        )
                    }
                    composable(route = AgendaRoutes.EVENT_ADD) {
                        EmptyScreen(
                            route = AgendaRoutes.EVENT_ADD
                        )
                    }
                    composable(route = AgendaRoutes.EVENT_EDIT) {
                        EmptyScreen(
                            route = AgendaRoutes.EVENT_EDIT
                        )
                    }
                    composable(route = AgendaRoutes.EVENT_DETAIL) {
                        EmptyScreen(
                            route = AgendaRoutes.EVENT_DETAIL
                        )
                    }
                    composable(route = AgendaRoutes.TASK_ADD) {
                        EmptyScreen(
                            route = AgendaRoutes.TASK_ADD
                        )
                    }
                    composable(route = AgendaRoutes.TASK_EDIT) {
                        EmptyScreen(
                            route = AgendaRoutes.TASK_EDIT
                        )
                    }
                    composable(route = AgendaRoutes.TASK_DETAIL) {
                        EmptyScreen(
                            route = AgendaRoutes.TASK_DETAIL
                        )
                    }
                    composable(route = AgendaRoutes.REMINDER_ADD) {
                        EmptyScreen(
                            route = AgendaRoutes.REMINDER_ADD
                        )
                    }
                    composable(route = AgendaRoutes.REMINDER_EDIT) {
                        EmptyScreen(
                            route = AgendaRoutes.REMINDER_EDIT
                        )
                    }
                    composable(route = AgendaRoutes.REMINDER_DETAIL) {
                        EmptyScreen(
                            route = AgendaRoutes.REMINDER_DETAIL
                        )
                    }
                }
            }
        }
    )
}
