package com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription

sealed class EditTitleOrDescriptionAction {
    data object OnBackIconClicked : EditTitleOrDescriptionAction()
    data object OnSaveClicked : EditTitleOrDescriptionAction()
    data class OnTextChanged(val text: String) : EditTitleOrDescriptionAction()
}