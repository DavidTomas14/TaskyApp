package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgendaRoot(
    agendaViewModel: AgendaViewModel = koinViewModel(),
) {
    AgendaScreen(
        state = agendaViewModel.state,
    )
}
