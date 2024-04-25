package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.davidtomas.taskyapp.core.presentation.util.ObserveAsEvents
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
        ?.collectAsStateWithLifecycle()
    val editedType = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>(AgendaRoutes.EDITED_TYPE_PARAM, null)
        ?.collectAsStateWithLifecycle()

    val deletedPhoto = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>(AgendaRoutes.IS_PHOTO_DELETED_PARAM, null)
        ?.collectAsStateWithLifecycle()

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
    }
    LaunchedEffect(key1 = deletedPhoto) {
        deletedPhoto?.value?.let { key ->
            agendaDetailViewModel.onAction(AgendaDetailAction.OnPhotoDeleted(key))
        }
    }
    ObserveAsEvents(agendaDetailViewModel.uiEvent) { event ->
        when (event) {
            is AgendaDetailUiEvent.NavigateUp -> {
                navController.navigateUp()
            }
            is AgendaDetailUiEvent.NavigateToPhotoDetail -> {
                navController.navigate(
                    "${AgendaRoutes.PHOTO_DETAIL}/" +
                        "${event.photoKey}"
                )
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
                            URLEncoder.encode(agendaDetailViewModel.state.description.ifEmpty { null }, "UTF-8")
                    )
                }

                is AgendaDetailAction.OnNavigateToEditTitleClick -> {
                    navController.navigate(
                        "${AgendaRoutes.AGENDA_EDIT_TEXT}/" +
                            "${EditType.TITLE.name}/" +
                            URLEncoder.encode(agendaDetailViewModel.state.title.ifEmpty { null }, "UTF-8")
                    )
                }

                else -> agendaDetailViewModel.onAction(agendaAction)
            }
        }
    )
}