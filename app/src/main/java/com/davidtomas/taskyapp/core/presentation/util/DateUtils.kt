package com.davidtomas.taskyapp.core.presentation.util

import android.os.Build
import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Locale

fun Long?.formatToMMDDYY(): String {
    val millis = this ?: Instant.now().toEpochMilli()
    val defaultZoneDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
    return defaultZoneDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
}

fun ZonedDateTime.formatToMMM(): String {
    return this.format(DateTimeFormatter.ofPattern("MMMM")).uppercase(Locale.ROOT)
}

fun ZonedDateTime.daysOfWeekIncludingGivenDate(): Map<DayOfWeek, ZonedDateTime> {
    val days = mutableListOf<ZonedDateTime>()
    val startOfWeek = this.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endOfWeek = this.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    var currentDate = startOfWeek
    while (!currentDate.isAfter(endOfWeek)) {
        days.add(currentDate)
        currentDate = currentDate.plusDays(1)
    }
    return DayOfWeek.values().zip(days).toMap()
}

fun ZonedDateTime.getStartAndEndOfDayMillis(): Pair<Long, Long> {
    val startOfDay = this
        .with(ChronoField.HOUR_OF_DAY, 0)
        .with(ChronoField.MINUTE_OF_HOUR, 0)
        .with(ChronoField.SECOND_OF_MINUTE, 0)
        .with(ChronoField.NANO_OF_SECOND, 0)

    val endOfDay = this
        .with(ChronoField.HOUR_OF_DAY, 23)
        .with(ChronoField.MINUTE_OF_HOUR, 59)
        .with(ChronoField.SECOND_OF_MINUTE, 59)
        .with(ChronoField.NANO_OF_SECOND, 999999999)

    val startOfDayMillis = startOfDay.toInstant().toEpochMilli()
    val endOfDayMillis = endOfDay.toInstant().toEpochMilli()
    return Pair(startOfDayMillis, endOfDayMillis)
}

fun Long?.formatToMMMdHHmm(): String {
    val millis = this ?: Instant.now().toEpochMilli()
    val defaultZoneDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
    return defaultZoneDateTime.format(DateTimeFormatter.ofPattern("MMM, d HH:mm"))
}

fun formatHoursAndMinutes(hours: Int, minutes: Int): String = String.format(
    Locale.getDefault(), "%02d:%02d", hours, minutes
)
fun ZonedDateTime.extractFromStartOfTheDayOfDateMillis() = this.toLocalDate().atStartOfDay(ZoneId.systemDefault())
    .toInstant().toEpochMilli()

fun ZonedDateTime.extractDayMillis() = this.toLocalTime().toSecondOfDay() * 1000

fun Long.extractFromStartOfTheDayOfDateMillis() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        LocalDate.ofInstant(
            Instant.ofEpochMilli(this),
            ZoneId.of("UTC")
        ).atStartOfDay(ZoneId.systemDefault())
            .toInstant().toEpochMilli()
    } else {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.timeInMillis
    }

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
