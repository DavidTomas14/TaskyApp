package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING

data class PhotoDetailState(
    val photoKey: String = String.EMPTY_STRING,
    val photoImageData: ByteArray = ByteArray(100),
)
