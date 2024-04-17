package com.davidtomas.taskyapp.features.agenda.data.event.local.mapper

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.PhotoEntity
import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel

fun PhotoEntity.toPhotoModel() = PhotoModel(
    key = key,
    imageData = imageData
)

fun PhotoModel.toPhotoEntity() = PhotoEntity().apply {
    key = this@toPhotoEntity.key
    imageData = this@toPhotoEntity.imageData
}