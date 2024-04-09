package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes

class PhotoDetailViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(PhotoDetailState())
        private set

    private val photoUrl =
        savedStateHandle.get<String>(AgendaRoutes.PHOTO_URL_PARAM)

    init {
        state = state.copy(
            photoUrl = photoUrl ?: String.EMPTY_STRING
        )
    }

    fun onAction(photoDetailAction: PhotoDetailAction) {
        when (photoDetailAction) {

            is PhotoDetailAction.OnDeleteIconClicked -> {

            }

            else -> Unit
        }
    }
}