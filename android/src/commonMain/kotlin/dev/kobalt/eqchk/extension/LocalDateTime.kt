package dev.kobalt.eqchk.android.extension

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toRelativeTimeString(to: LocalDateTime): String {
    /*
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
        days > 0 -> "$days d $hours h"
        hours > 0 -> "$hours h $minutes min"
        minutes > 0 -> "$minutes min"
        else -> "<1 min"
    }*/
    return "TODO"
}

/*
fun TemporalAccessor.toString(format: DateTimeFormatter): String {
    return format.format(this)
}

fun TemporalAccessor.toString(format: String): String {
    return toString(DateTimeFormatter.ofPattern(format))
}
*/