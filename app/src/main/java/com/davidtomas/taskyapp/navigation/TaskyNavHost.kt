package com.davidtomas.taskyapp.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.AgendaRoot
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.AgendaDetailRoot
import com.davidtomas.taskyapp.features.agenda.presentation.editText.EditTextRoot
import com.davidtomas.taskyapp.features.agenda.presentation.photoDetail.PhotoDetailRoot
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
        content = {
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
                    composable(
                        route =
                        "${AgendaRoutes.AGENDA_DETAIL}/" +
                            "{${AgendaRoutes.AGENDA_TYPE_PARAM}}/" +
                            "{${AgendaRoutes.SCREEN_MODE_PARAM}}/" +
                            "{${AgendaRoutes.AGENDA_ITEM_ID_PARAM}}",
                        arguments = listOf(
                            navArgument(AgendaRoutes.AGENDA_ITEM_ID_PARAM) {
                                type = NavType.StringType
                                nullable = true
                            },
                            navArgument(AgendaRoutes.AGENDA_TYPE_PARAM) {
                                type = NavType.StringType
                            },
                            navArgument(AgendaRoutes.SCREEN_MODE_PARAM) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        AgendaDetailRoot(navController = navController)
                    }
                }
                composable(
                    route = "${AgendaRoutes.AGENDA_EDIT_TEXT}/" +
                        "{${AgendaRoutes.EDIT_TYPE_PARAM}}/" +
                        "{${AgendaRoutes.EDIT_TEXT_PARAM}}",
                    arguments = listOf(
                        navArgument(AgendaRoutes.EDIT_TYPE_PARAM) {
                            type = NavType.StringType
                        },
                        navArgument(AgendaRoutes.EDIT_TEXT_PARAM) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    EditTextRoot(
                        navController = navController
                    )
                }
                composable(
                    route = "${AgendaRoutes.PHOTO_DETAIL}/" +
                        "{${AgendaRoutes.PHOTO_URI_PARAM}}",
                    arguments = listOf(
                        navArgument(AgendaRoutes.PHOTO_URI_PARAM) {
                            type = NavType.StringType
                        },
                    )
                ) {
                    PhotoDetailRoot(
                        navController = navController
                    )
                }
            }
        }
    )
}