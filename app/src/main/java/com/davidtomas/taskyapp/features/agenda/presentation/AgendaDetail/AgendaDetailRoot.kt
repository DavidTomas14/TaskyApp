package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgendaDetailRoot(
    navController: NavHostController,
    agendaDetailViewModel: AgendaDetailViewModel = koinViewModel(),
) {
    AgendaDetailScreen(
        state = agendaDetailViewModel.state,
        onAction = { agendaAction ->
            when (agendaAction) {
                is AgendaDetailAction.OnCloseDetailIconClick -> {
                    navController.navigateUp()
                }

                else -> agendaDetailViewModel.onAction(agendaAction)
            }
        }
    )
}