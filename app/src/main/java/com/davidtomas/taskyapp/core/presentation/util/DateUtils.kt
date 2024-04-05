package com.davidtomas.taskyapp.core.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun Long?.formatToMMDDYY(): String {
    val millis = this ?: Instant.now().toEpochMilli()
    val defaultZoneDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
    return defaultZoneDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
}

fun formatHoursAndMinutes(hours: Int, minutes: Int): String = String.format(
    Locale.getDefault(), "%02d:%02d", hours, minutes
)

@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.extractDateMillis() = this.toLocalDate().atStartOfDay(ZoneId.systemDefault())
    .toInstant().toEpochMilli()

@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.extractDayMillis() = this.toLocalTime().toSecondOfDay() * 1000

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun Long.extractDateMillis() = LocalDate.ofInstant(
    Instant.ofEpochMilli(this),
    ZoneId.of("UTC")
).atStartOfDay(ZoneId.systemDefault())
    .toInstant().toEpochMilli()

fun Long.formatToDayHourOrMinutes(): String {
    val duration = Duration.ofMillis(this)
    return when {
        duration.toHours() < 1 -> String.format(
            Locale.getDefault(),
            "%d minutes before",
            duration.toMinutes()
        )

        duration.toHours() == 1L -> String.format(
            Locale.getDefault(),
            "1 hour before",
        )

        duration.toDays() < 1 -> String.format(
            Locale.getDefault(),
            "%d hours before",
            duration.toHours()
        )

        duration.toDays() == 1L -> String.format(
            Locale.getDefault(),
            "1 day before",
        )

        else -> String.format(
            Locale.getDefault(),
            "%d days before",
            duration.toDays()
        )
    }
}
