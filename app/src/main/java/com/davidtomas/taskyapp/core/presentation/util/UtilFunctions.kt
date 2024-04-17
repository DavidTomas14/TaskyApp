package com.davidtomas.taskyapp.core.presentation.util

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

fun String.toInitials(): String {
    val words = this.split(" ").filter { it.isNotBlank() }
    return when {
        words.size == 1 -> words[0].take(2).uppercase()
        words.size == 2 -> words[0].take(1).uppercase() + words[1].take(1).uppercase()
        words.size > 2 -> words[0].take(1).uppercase() + words.last().take(1).uppercase()
        else -> ""
    }
}

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = flow, key2 = lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}

fun Bitmap.convertToByteArrayMax1MB(): ByteArray {
    val stream = ByteArrayOutputStream()
    var quality = 100
    do {
        stream.reset() // Clear the buffer
        this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        if (stream.size() > 1 * 1024 * 1024) {
            quality -= 10 // Decrease quality by 10%
        }
    } while (stream.size() > 1 * 1024 * 1024 && quality > 0)
    return stream.toByteArray()
}