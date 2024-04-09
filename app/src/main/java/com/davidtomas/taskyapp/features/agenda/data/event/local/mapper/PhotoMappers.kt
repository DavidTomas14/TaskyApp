package com.davidtomas.taskyapp.features.agenda.data.event.local.mapper

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.PhotoEntity
import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel

fun PhotoEntity.toPhotoModel() = PhotoModel(
    key = key,
    uri = uri
)

fun PhotoModel.toPhotoEntity() = PhotoEntity().apply {
    key = this@toPhotoEntity.key
    uri = this@toPhotoEntity.uri
}