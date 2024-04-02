package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription.EditType
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgendaDetailRoot(
    navController: NavHostController,
    agendaDetailViewModel: AgendaDetailViewModel = koinViewModel(),
) {
    LaunchedEffect(key1 = true) {
        agendaDetailViewModel.uiEvent.collect { event ->
            when (event) {
                is AgendaDetailUiEvent.NavigateUp -> {
                    navController.navigateUp()
                }

                is AgendaDetailUiEvent.NavigateToEditDescription -> {
                    navController.navigate(
                        "${AgendaRoutes.AGENDA_EDIT_TEXT}/" +
                            "${EditType.DESCRIPTION.name}/" +
                            agendaDetailViewModel.state.description
                    )
                }

                is AgendaDetailUiEvent.NavigateToEditTitle -> {
                    navController.navigate(
                        "${AgendaRoutes.AGENDA_EDIT_TEXT}/" +
                            "${EditType.TITLE.name}/" +
                            agendaDetailViewModel.state.title
                    )
                }
            }
        }
    }
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