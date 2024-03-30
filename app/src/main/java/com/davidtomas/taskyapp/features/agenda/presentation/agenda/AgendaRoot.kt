package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
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
                    when (agendaAction.agendaModel) {
                        is EventModel -> {
                            navController.navigate(AgendaRoutes.EVENT_DETAIL)
                        }

                        is TaskModel -> {
                            navController.navigate(AgendaRoutes.TASK_DETAIL)
                        }

                        is ReminderModel -> {
                            navController.navigate(AgendaRoutes.REMINDER_DETAIL)
                        }
                    }
                }

                is AgendaAction.OnEditAgendaItemClicked -> {
                    when (agendaAction.agendaModel) {
                        is EventModel -> {
                            navController.navigate(AgendaRoutes.EVENT_EDIT)
                        }

                        is TaskModel -> {
                            navController.navigate(AgendaRoutes.TASK_EDIT)
                        }

                        is ReminderModel -> {
                            navController.navigate(AgendaRoutes.REMINDER_EDIT)
                        }
                    }
                }

                is AgendaAction.OnAgendaItemClicked ->
                    when (agendaAction.agendaModel) {
                        is EventModel -> {
                            navController.navigate(AgendaRoutes.EVENT_DETAIL)
                        }

                        is TaskModel -> {
                            navController.navigate(AgendaRoutes.TASK_DETAIL)
                        }

                        is ReminderModel -> {
                            navController.navigate(AgendaRoutes.REMINDER_DETAIL)
                        }
                    }

                else -> agendaViewModel.onAction(agendaAction)
            }
        }

    )
}