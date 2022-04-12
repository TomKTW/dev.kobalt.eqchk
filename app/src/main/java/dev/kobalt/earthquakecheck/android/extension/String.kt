package dev.kobalt.earthquakecheck.android.extension

import android.text.Html
import android.text.Spanned
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toJsonElement(): JsonElement = Json.parseToJsonElement(this)

fun String.toLocalDateTime(pattern: String): LocalDateTime =
    toLocalDateTime(DateTimeFormatter.ofPattern(pattern))

fun String.toLocalDateTime(format: DateTimeFormatter): LocalDateTime =
    LocalDateTime.parse(this, format)

@Suppress("DEPRECATION")
fun String.toSpannedHtml(): Spanned = Html.fromHtml(this)