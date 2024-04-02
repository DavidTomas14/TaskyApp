package com.davidtomas.taskyapp.core.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toInitials(): String {
    val words = this.split(" ").filter { it.isNotBlank() }
    return when {
        words.size > 1 -> words[0].take(1).uppercase() + words[1].take(1).uppercase()
        words.isNotEmpty() -> words[0].take(2).uppercase()
        else -> ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long?.formatToMMDDYY(): String {
    val millis = this ?: Instant.now().toEpochMilli()
    val defaultZoneDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
    return defaultZoneDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
}

fun formatHoursAndMinutes(hours: Int, minutes: Int): String =
    "${if (hours < 12) "0$hours" else hours}:" + "${if (minutes < 10) "0$minutes" else minutes}"