package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgendaRoot(
    agendaViewModel: AgendaViewModel = koinViewModel(),
    navController: NavHostController,
) {
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
                                    "${AgendaType.EVENT.name}/" +
                                    "${ScreenMode.EDIT_ADD.name}/" +
                                    agendaAction.agendaModel.id
                            )
                        }

                        is TaskModel -> {
                            navController.navigate(
                                "${AgendaRoutes.AGENDA_DETAIL}/" +
                                    "${AgendaType.TASK.name}/" +
                                    "${ScreenMode.EDIT_ADD.name}/" +
                                    agendaAction.agendaModel.id
                            )
                        }

                        is ReminderModel -> {
                            navController.navigate(
                                "${AgendaRoutes.AGENDA_DETAIL}/" +
                                    "${AgendaType.REMINDER.name}/" +
                                    "${ScreenMode.EDIT_ADD.name}/" +
                                    agendaAction.agendaModel.id
                            )
                        }
                    }
                }

                is AgendaAction.OnAgendaItemClicked -> {
                    navigateToDetailNonEditable(agendaAction.agendaModel, navController)
                }

                else -> agendaViewModel.onAction(agendaAction)
            }
        }
    )
}
private fun navigateToDetailNonEditable(
    agendaModel: AgendaModel,
    navController: NavHostController
) {
    when (agendaModel) {
        is EventModel -> {
            navController.navigate(
                "${AgendaRoutes.AGENDA_DETAIL}/" +
                    "${AgendaType.EVENT.name}/" +
                    "${ScreenMode.REVIEW.name}/" +
                    agendaModel.id
            )
        }

        is TaskModel -> {
            navController.navigate(
                "${AgendaRoutes.AGENDA_DETAIL}/" +
                    "${AgendaType.TASK.name}/" +
                    "${ScreenMode.REVIEW.name}/" +
                    agendaModel.id
            )
        }

        is ReminderModel -> {
            navController.navigate(
                "${AgendaRoutes.AGENDA_DETAIL}/" +
                    "${AgendaType.REMINDER.name}/" +
                    "${ScreenMode.REVIEW.name}/" +
                    agendaModel.id
            )
        }
    }
}