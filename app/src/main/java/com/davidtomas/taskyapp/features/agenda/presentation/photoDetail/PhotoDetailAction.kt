package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

sealed class PhotoDetailAction {
    data object OnCloseIconClicked : PhotoDetailAction()
    data object OnDeleteIconClicked : PhotoDetailAction()
}