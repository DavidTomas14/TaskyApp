package com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditTitleOrDescriptionRoot(
    navController: NavHostController,
    editTitleOrDescriptionViewModel: EditTitleOrDescriptionViewModel = koinViewModel(),
) {
    EditTitleOrDescriptionScreen(
        state = editTitleOrDescriptionViewModel.state,
        onAction = { agendaAction ->
            when (agendaAction) {
                is EditTitleOrDescriptionAction.OnBackIconClicked -> {
                    navController.navigateUp()
                }

                is EditTitleOrDescriptionAction.OnSaveClicked -> {
                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(AgendaRoutes.EDITED_TYPE_PARAM, editTitleOrDescriptionViewModel.state.editType.name)

                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(AgendaRoutes.EDITED_TEXT_PARAM, editTitleOrDescriptionViewModel.state.text)
                    navController.popBackStack()
                }

                else -> editTitleOrDescriptionViewModel.onAction(agendaAction)
            }
        }
    )
}