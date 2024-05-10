package dev.kobalt.eqchk.android.extension

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

fun String.toJsonElement(): JsonElement = Json.parseToJsonElement(this)

@OptIn(FormatStringsInDatetimeFormats::class)
fun String.toLocalDateTime(pattern: String): LocalDateTime =
    toLocalDateTime(DateTimeFormat.formatAsKotlinBuilderDsl(DateTimeComponents.Format {
        byUnicodePattern("uuuu-MM-dd'T'HH:mm:ss[.SSS]Z")
    }))

fun String.toLocalDateTime(format: DateTimeFormat<LocalDateTime>): LocalDateTime =
    LocalDateTime.parse(this, format)

/*
@Suppress("DEPRECATION")
fun String.toSpannedHtml(): Spanned = Html.fromHtml(this) */