package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
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