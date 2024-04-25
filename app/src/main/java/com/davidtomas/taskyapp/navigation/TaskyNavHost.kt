package com.davidtomas.taskyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.AgendaRoot
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.AgendaDetailRoot
import com.davidtomas.taskyapp.features.agenda.presentation.editText.EditTextRoot
import com.davidtomas.taskyapp.features.agenda.presentation.photoDetail.PhotoDetailRoot
import com.davidtomas.taskyapp.features.auth.presentation._common.navigation.AuthRoutes
import com.davidtomas.taskyapp.features.auth.presentation.login.LoginRoot
import com.davidtomas.taskyapp.features.auth.presentation.register.RegisterRoot

@Composable
internal fun TaskyNavHost(
    isAuthenticated: Boolean,
    isAuthChecked: Boolean
) {
    val navController = rememberNavController()

    if (isAuthChecked) {
        NavHost(
            navController = navController,
            startDestination = if (isAuthenticated) AgendaRoutes.AGENDA_GRAPH else AuthRoutes.AUTH_GRAPH
        ) {
            navigation(
                startDestination = AuthRoutes.LOGIN,
                route = AuthRoutes.AUTH_GRAPH
            ) {
                composable(route = AuthRoutes.LOGIN) {
                    LoginRoot(
                        navController = navController
                    )
                }
                composable(route = AuthRoutes.REGISTER) {
                    RegisterRoot(
                        navController = navController
                    )
                }
            }

            navigation(
                startDestination = AgendaRoutes.AGENDA,
                route = AgendaRoutes.AGENDA_GRAPH
            ) {
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
                            defaultValue = ""
                        },
                        navArgument(AgendaRoutes.AGENDA_TYPE_PARAM) {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument(AgendaRoutes.SCREEN_MODE_PARAM) {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    ),
                    deepLinks =
                    listOf(
                        navDeepLink {
                            uriPattern =
                                "tasky://detail/" +
                                "{${AgendaRoutes.AGENDA_TYPE_PARAM}}/" +
                                "{${AgendaRoutes.SCREEN_MODE_PARAM}}/" +
                                "{${AgendaRoutes.AGENDA_ITEM_ID_PARAM}}"
                        },
                    ),
                ) {
                    AgendaDetailRoot(
                        navController = navController
                    )
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
                        "{${AgendaRoutes.PHOTO_KEY_PARAM}}",
                    arguments = listOf(
                        navArgument(AgendaRoutes.PHOTO_KEY_PARAM) {
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
    }
}