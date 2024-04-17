package com.davidtomas.taskyapp.features.agenda.domain.model

data class PhotoModel(
    val key: String,
    val imageData: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhotoModel

        if (key != other.key) return false
        if (!imageData.contentEquals(other.imageData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + imageData.contentHashCode()
        return result
    }
}
