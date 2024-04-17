package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.features.agenda.domain.repository.PhotoRepository
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    private val photoRepository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(PhotoDetailState())
        private set

    private val photoKey =
        savedStateHandle.get<String>(AgendaRoutes.PHOTO_KEY_PARAM)

    init {
        viewModelScope.launch {
            if (photoKey != null) {
                val photoImageData = photoRepository.getImageData(photoKey)
                state = state.copy(
                    photoKey = photoKey,
                    photoImageData = photoImageData ?: ByteArray(100)
                )
            }
        }
    }
}