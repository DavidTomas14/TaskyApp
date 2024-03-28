package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.features.agenda.domain.useCase.GetAgendaUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AgendaViewModel(
    private val getAgendaUseCase: GetAgendaUseCase
) : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set

    init {
        viewModelScope.launch {
            getAgendaUseCase().fold(
                onError = {
                    Log.d("Agenda", it.toString())
                },
                onSuccess = {
                    Log.d("Agenda", it.toString())
                    state = state.copy(
                        agendaItems = it.first()
                    )
                }
            )
        }
    }
}