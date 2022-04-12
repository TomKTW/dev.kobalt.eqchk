package dev.kobalt.earthquakecheck.android.event

import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toEventEntity(): EventEntity {
    return EventEntity(
        id = this[EventTable.uid],
        location = this[EventTable.location],
        timestamp = this[EventTable.timestamp],
        estimatedIntensity = this[EventTable.estimatedIntensity],
        communityIntensity = this[EventTable.communityIntensity],
        communityResponseCount = this[EventTable.communityResponseCount],
        magnitude = this[EventTable.magnitude],
        latitude = this[EventTable.latitude],
        longitude = this[EventTable.longitude],
        depth = this[EventTable.depth],
        detailsUrl = this[EventTable.detailsUrl],
        tectonicSummary = this[EventTable.tectonicSummary],
        impactSummary = this[EventTable.impactSummary]
    )
}