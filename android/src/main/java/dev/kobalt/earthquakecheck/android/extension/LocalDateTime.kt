package dev.kobalt.earthquakecheck.android.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAccessor

fun LocalDateTime.toRelativeTimeString(to: LocalDateTime): String {
    var tempDateTime = LocalDateTime.from(this)
    val years = tempDateTime.until(to, ChronoUnit.YEARS)
    tempDateTime = tempDateTime.plusYears(years)
    val months = tempDateTime.until(to, ChronoUnit.MONTHS)
    tempDateTime = tempDateTime.plusMonths(months)
    val days = tempDateTime.until(to, ChronoUnit.DAYS)
    tempDateTime = tempDateTime.plusDays(days)
    val hours = tempDateTime.until(to, ChronoUnit.HOURS)
    tempDateTime = tempDateTime.plusHours(hours)
    val minutes = tempDateTime.until(to, ChronoUnit.MINUTES)
    // tempDateTime = tempDateTime.plusMinutes(minutes)
    // val seconds = tempDateTime.until(to, ChronoUnit.SECONDS)
    return when {
        days > 0 -> this.toString(DateTimeFormatter.ISO_DATE)
        hours > 0 -> "$hours h"
        minutes > 0 -> "$minutes min"
        else -> "<1 min"
    }
}

fun TemporalAccessor.toString(format: DateTimeFormatter): String {
    return format.format(this)
}

fun TemporalAccessor.toString(format: String): String {
    return toString(DateTimeFormatter.ofPattern(format))
}