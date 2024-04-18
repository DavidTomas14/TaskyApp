package com.davidtomas.taskyapp.features.agenda.data.event.local.mapper

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.PhotoEntity
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel

fun PhotoEntity.toPhotoModel() = PhotoModel(
    key = key,
    imageData = imageData,
    modificationType = syncType?.let { ModificationType.valueOf(it) }
)

fun PhotoModel.toPhotoEntity(modType: ModificationType?) = PhotoEntity().apply {
    key = this@toPhotoEntity.key
    imageData = this@toPhotoEntity.imageData
    syncType = if (modType == null) null else this@toPhotoEntity.modificationType?.name
}