package com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes

class EditTitleOrDescriptionViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(EditTitleOrDescriptionState())
        private set

    private val editType =
        savedStateHandle.get<String>(AgendaRoutes.EDIT_TYPE_PARAM)?.let { EditType.valueOf(it) }

    private val editText =
        savedStateHandle.get<String>(AgendaRoutes.EDIT_TEXT_PARAM)

    init {
        state = state.copy(
            editType = editType ?: EditType.TITLE,
            text = editText ?: String.EMPTY_STRING
        )
    }

    fun onAction(editTitleOrDescriptionAction: EditTitleOrDescriptionAction) {
        when (editTitleOrDescriptionAction) {

            is EditTitleOrDescriptionAction.OnTextChanged -> {
                state = state.copy(text = editTitleOrDescriptionAction.text)
            }

            else -> Unit
        }
    }
}