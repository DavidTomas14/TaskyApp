package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.features.agenda.domain.model.EditType
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder

@Composable
fun AgendaDetailRoot(
    navController: NavHostController,
    agendaDetailViewModel: AgendaDetailViewModel = koinViewModel(),
) {
    val editedText = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>(AgendaRoutes.EDITED_TEXT_PARAM, null)
        ?.collectAsState()
    val editedType = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>(AgendaRoutes.EDITED_TYPE_PARAM, null)
        ?.collectAsState()

    LaunchedEffect(key1 = editedText, key2 = editedType) {
        editedText?.value?.let { editedText ->
            editedType?.value?.let { editedType ->
                val enumEditedType = EditType.valueOf(editedType)
                when (enumEditedType) {
                    EditType.TITLE -> {
                        agendaDetailViewModel.onAction(AgendaDetailAction.OnTitleChanged(editedText))
                    }

                    EditType.DESCRIPTION -> {
                        agendaDetailViewModel.onAction(
                            AgendaDetailAction.OnDescriptionChanged(
                                editedText
                            )
                        )
                    }
                }
            }
        }
        agendaDetailViewModel.uiEvent.collect { event ->
            when (event) {
                is AgendaDetailUiEvent.NavigateUp -> {
                    navController.navigateUp()
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

                is AgendaDetailAction.OnNavigateToEditDescriptionClick -> {
                    navController.navigate(
                        "${AgendaRoutes.AGENDA_EDIT_TEXT}/" +
                                "${EditType.DESCRIPTION.name}/" +
                                URLEncoder.encode(agendaDetailViewModel.state.description, "UTF-8")
                    )
                }

                is AgendaDetailAction.OnNavigateToEditTitleClick -> {
                    navController.navigate(
                        "${AgendaRoutes.AGENDA_EDIT_TEXT}/" +
                                "${EditType.TITLE.name}/" +
                                URLEncoder.encode(agendaDetailViewModel.state.title, "UTF-8")

                    )
                }

                else -> agendaDetailViewModel.onAction(agendaAction)
            }
        }
    )
}