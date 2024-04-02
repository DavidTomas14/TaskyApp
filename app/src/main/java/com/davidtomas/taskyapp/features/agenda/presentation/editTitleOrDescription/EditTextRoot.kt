package com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditTextRoot(
    navController: NavHostController,
    editTextViewModel: EditTextViewModel = koinViewModel(),
) {
    EditTextScreen(
        state = editTextViewModel.state,
        onAction = { agendaAction ->
            when (agendaAction) {
                is EditTextAction.OnBackIconClicked -> {
                    navController.navigateUp()
                }

                is EditTextAction.OnSaveClicked -> {
                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(AgendaRoutes.EDITED_TYPE_PARAM, editTextViewModel.state.editType.name)

                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(AgendaRoutes.EDITED_TEXT_PARAM, editTextViewModel.state.text)
                    navController.popBackStack()
                }

                else -> editTextViewModel.onAction(agendaAction)
            }
        }
    )
}