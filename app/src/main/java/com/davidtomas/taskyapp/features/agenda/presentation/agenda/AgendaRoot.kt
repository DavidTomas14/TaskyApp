package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.core.presentation.util.ObserveAsEvents
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType.EVENT
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType.REMINDER
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType.TASK
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.AgendaUiEvent
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.NotificationPermissionTextProvider
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.PermissionDialog
import com.davidtomas.taskyapp.features.auth.presentation._common.navigation.AuthRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgendaRoot(
    agendaViewModel: AgendaViewModel = koinViewModel(),
    navController: NavHostController,
) {
    val context = LocalContext.current
    val activity = context as Activity
    var showPermissionDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val notificationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                showPermissionDialog = true
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    ObserveAsEvents(agendaViewModel.uiEvent) { event ->
        when (event) {
            is AgendaUiEvent.NavigateToLogin -> {
                navController.navigate(
                    route = AuthRoutes.AUTH_GRAPH
                ) {
                    popUpTo(AgendaRoutes.AGENDA_GRAPH) {
                        inclusive = true
                    }
                }
            }
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (showPermissionDialog)
            PermissionDialog(
                permissionTextProvider = NotificationPermissionTextProvider(),
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                onDismiss = { showPermissionDialog = false },
                onOkClick = {
                    showPermissionDialog = false
                    notificationPermissionResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                },
                onGoToAppSettingsClick = { activity.openAppSettings() }
            )
    }

    AgendaScreen(
        state = agendaViewModel.state,
        onAction = { agendaAction ->
            when (agendaAction) {
                is AgendaAction.OnOpenAgendaItemClicked -> {
                    navigateToDetailNonEditable(agendaAction.agendaModel, navController)
                }

                is AgendaAction.OnEditAgendaItemClicked -> {
                    when (agendaAction.agendaModel) {
                        is EventModel -> {
                            navController.navigate(
                                "${AgendaRoutes.AGENDA_DETAIL}/" +
                                    "${EVENT.name}/" +
                                    "${ScreenMode.EDIT_ADD.name}/" +
                                    agendaAction.agendaModel.id
                            )
                        }

                        is TaskModel -> {
                            navController.navigate(
                                "${AgendaRoutes.AGENDA_DETAIL}/" +
                                    "${TASK.name}/" +
                                    "${ScreenMode.EDIT_ADD.name}/" +
                                    agendaAction.agendaModel.id
                            )
                        }

                        is ReminderModel -> {
                            navController.navigate(
                                "${AgendaRoutes.AGENDA_DETAIL}/" +
                                    "${REMINDER.name}/" +
                                    "${ScreenMode.EDIT_ADD.name}/" +
                                    agendaAction.agendaModel.id
                            )
                        }
                    }
                }

                is AgendaAction.OnAgendaItemClicked -> {
                    navigateToDetailNonEditable(agendaAction.agendaModel, navController)
                }

                is AgendaAction.OnAddAgendaItemOptionClicked -> {
                    navController.navigate(
                        "${AgendaRoutes.AGENDA_DETAIL}/" +
                            "${agendaAction.agendaType.name}/" +
                            "${ScreenMode.EDIT_ADD.name}/" +
                            null
                    )
                }

                else -> agendaViewModel.onAction(agendaAction)
            }
        }
    )
}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

private fun navigateToDetailNonEditable(
    agendaModel: AgendaModel,
    navController: NavHostController
) {
    when (agendaModel) {
        is EventModel -> {
            navController.navigate(
                "${AgendaRoutes.AGENDA_DETAIL}/" +
                    "${EVENT.name}/" +
                    "${ScreenMode.REVIEW.name}/" +
                    agendaModel.id
            )
        }

        is TaskModel -> {
            navController.navigate(
                "${AgendaRoutes.AGENDA_DETAIL}/" +
                    "${TASK.name}/" +
                    "${ScreenMode.REVIEW.name}/" +
                    agendaModel.id
            )
        }

        is ReminderModel -> {
            navController.navigate(
                "${AgendaRoutes.AGENDA_DETAIL}/" +
                    "${REMINDER.name}/" +
                    "${ScreenMode.REVIEW.name}/" +
                    agendaModel.id
            )
        }
    }
}