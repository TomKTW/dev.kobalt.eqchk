package dev.kobalt.eqchk.android.event

import kotlinx.serialization.json.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun JsonObject.toEventEntity(): EventEntity {
    val properties = this["properties"]?.jsonObject
    val geometry = this["geometry"]?.jsonObject
    val coordinates = geometry?.get("coordinates")?.jsonArray
    val products = properties?.get("products")?.jsonObject
    return EventEntity(
        id = this["id"]?.jsonPrimitive?.contentOrNull!!,
        location = properties?.get("place")?.jsonPrimitive?.contentOrNull,
        timestamp = properties?.get("time")?.jsonPrimitive?.longOrNull?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.of("UTC"))
        },
        estimatedIntensity = properties?.get("mmi")?.jsonPrimitive?.contentOrNull?.toBigDecimalOrNull(),
        communityIntensity = properties?.get("cdi")?.jsonPrimitive?.contentOrNull?.toBigDecimalOrNull(),
        communityResponseCount = properties?.get("felt")?.jsonPrimitive?.intOrNull,
        magnitude = properties?.get("mag")?.jsonPrimitive?.contentOrNull?.toBigDecimalOrNull(),
        latitude = coordinates?.get(1)?.jsonPrimitive?.doubleOrNull,
        longitude = coordinates?.get(0)?.jsonPrimitive?.doubleOrNull,
        depth = coordinates?.get(2)?.jsonPrimitive?.doubleOrNull,
        detailsUrl = properties?.get("url")?.jsonPrimitive?.contentOrNull,
        tectonicSummary = products?.get("general-text")?.jsonArray?.getOrNull(0)?.jsonObject?.get("contents")?.jsonObject?.get(
            ""
        )?.jsonObject?.get("bytes")?.jsonPrimitive?.contentOrNull,
        impactSummary = products?.get("impact-text")?.jsonArray?.getOrNull(0)?.jsonObject?.get("contents")?.jsonObject?.get(
            ""
        )?.jsonObject?.get("bytes")?.jsonPrimitive?.contentOrNull
    )
}