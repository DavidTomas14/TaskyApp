package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

sealed class PhotoDetailUiEvent {
    data object NavigateUp : PhotoDetailUiEvent()
}