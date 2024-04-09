package com.davidtomas.taskyapp.features.agenda.presentation.editText

sealed class EditTextAction {
    data object OnBackIconClicked : EditTextAction()
    data object OnSaveClicked : EditTextAction()
    data class OnTextChanged(val text: String) : EditTextAction()
}