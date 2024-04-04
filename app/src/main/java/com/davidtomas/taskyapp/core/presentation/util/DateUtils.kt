package com.davidtomas.taskyapp.core.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
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
fun Long.formatToDayHourOrMinutes(): String =
    when {
        this.millisToHour() < 1 -> String.format(
            Locale.getDefault(),
            "%d minutes before",
            this.millisToMinute()
        )

        this.millisToHour() == 1 -> String.format(
            Locale.getDefault(),
            "%d hour before",
            this.millisToHour()
        )

        this.millisToDay() < 1 -> String.format(
            Locale.getDefault(),
            "%d hours before",
            this.millisToHour()
        )

        this.millisToDay() == 1 -> String.format(
            Locale.getDefault(),
            "%d day before",
            this.millisToDay()
        )

        else -> String.format(
            Locale.getDefault(),
            "%d days before",
            this.millisToDay()
        )
    }

fun Long.millisToDay() = (this / (3600 * 1000 * 24)).toInt()

fun Long.millisToHour() = (this / (3600 * 1000)).toInt()
fun Long.millisToMinute() = (this / (60 * 1000)).toInt()

fun Int.hourToMillis() = (this * 3600 * 1000).toLong()

fun Int.minuteToMillis() = (this * 60 * 1000).toLong()
