package dev.kobalt.earthquakecheck.android.event

import dev.kobalt.earthquakecheck.android.extension.toJsonElement
import dev.kobalt.earthquakecheck.android.extension.transaction
import dev.kobalt.earthquakecheck.android.main.MainApplication
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventRepository(private val application: MainApplication) {

    val database = Database.connect(
        "jdbc:h2:${application.native.filesDir.canonicalPath}/database",
        "org.h2.Driver"
    ).also {
        it.transaction {
            SchemaUtils.createMissingTablesAndColumns(EventTable)
        }
    }

    fun selectList(): List<EventEntity> = database.transaction {
        EventTable.selectAll()
            .orderBy(EventTable.timestamp, SortOrder.DESC)
            .map { it.toEventEntity() }
    }

    fun selectItem(uid: String): EventEntity? = database.transaction {
        EventTable.select { (EventTable.uid eq uid) }.singleOrNull()
            ?.toEventEntity()
    }

    fun getLatestItem(): EventEntity? {
        return EventTable.selectAll()
            .orderBy(EventTable.timestamp, SortOrder.DESC)
            .singleOrNull()?.toEventEntity()
    }

    fun reload(list: List<EventEntity>) = database.transaction {
        EventTable.deleteAll()
        list.forEach { entity ->
            EventTable.insert {
                it[EventTable.uid] = entity.id.orEmpty()
                it[EventTable.location] = entity.location.orEmpty()
                it[EventTable.timestamp] = entity.timestamp ?: LocalDateTime.MIN
                it[EventTable.estimatedIntensity] = entity.estimatedIntensity ?: BigDecimal.ZERO
                it[EventTable.communityIntensity] = entity.communityIntensity ?: BigDecimal.ZERO
                it[EventTable.communityResponseCount] = entity.communityResponseCount ?: 0
                it[EventTable.magnitude] = entity.magnitude ?: BigDecimal.ZERO
                it[EventTable.latitude] = entity.latitude ?: 0.0
                it[EventTable.longitude] = entity.longitude ?: 0.0
                it[EventTable.depth] = entity.depth ?: 0.0
                it[EventTable.detailsUrl] = entity.detailsUrl.orEmpty()
                it[EventTable.tectonicSummary] = entity.tectonicSummary.orEmpty()
                it[EventTable.impactSummary] = entity.impactSummary.orEmpty()
            }
        }
    }

    suspend fun fetchItem(id: String?): EventEntity? {
        application.httpClient.get<HttpStatement>(HttpRequestBuilder().apply {
            url {
                protocol = URLProtocol.HTTPS
                host = "earthquake.usgs.gov"
                encodedPath = "/fdsnws/event/1/query"
                parameters.apply {
                    this["format"] = "geojson"
                    id?.let { this["eventid"] = it }
                }
            }
        }).execute().let { response ->
            return when {
                response.status.isSuccess() -> {
                    response.readText().toJsonElement().jsonObject.toEventEntity()
                }
                response.status.value == 404 -> null
                else -> throw Exception()
            }
        }
    }

    suspend fun fetch(
        minMagnitude: Int? = null,
        maxMagnitude: Int? = null,
        minEstimatedIntensity: Int? = null,
        maxEstimatedIntensity: Int? = null,
        minCommunityIntensity: Int? = null,
        maxCommunityIntensity: Int? = null,
        minDepth: Int? = null,
        maxDepth: Int? = null,
        minTimestamp: LocalDateTime? = null,
        maxTimestamp: LocalDateTime? = null,
        latitude: Double? = null,
        longitude: Double? = null,
        range: Double? = null,
        limit: Int? = null
    ): List<EventEntity> {
        application.httpClient.get<HttpStatement>(HttpRequestBuilder().apply {
            url {
                protocol = URLProtocol.HTTPS
                host = "earthquake.usgs.gov"
                encodedPath = "/fdsnws/event/1/query"
                parameters.apply {
                    this["format"] = "geojson"
                    minMagnitude?.let { this["minmagnitude"] = it.toString() }
                    maxMagnitude?.let { this["maxmagnitude"] = it.toString() }
                    minEstimatedIntensity?.let { this["minmmi"] = it.toString() }
                    maxEstimatedIntensity?.let { this["maxmmi"] = it.toString() }
                    minCommunityIntensity?.let { this["mincdi"] = it.toString() }
                    maxCommunityIntensity?.let { this["maxcdi"] = it.toString() }
                    minDepth?.let { this["mindepth"] = it.toString() }
                    maxDepth?.let { this["maxdepth"] = it.toString() }
                    minTimestamp?.let {
                        this["starttime"] = it.format(DateTimeFormatter.ISO_DATE_TIME)
                    }
                    maxTimestamp?.let {
                        this["endtime"] = it.format(DateTimeFormatter.ISO_DATE_TIME)
                    }
                    latitude?.let { this["latitude"] = it.toString() }
                    longitude?.let { this["longitude"] = it.toString() }
                    range?.let { this["maxradiuskm"] = it.toString() }
                    this["limit"] = limit?.toString() ?: "100"
                }
            }
        }).execute().let { response ->
            when {
                response.status.isSuccess() -> {
                    return response.readText().toJsonElement()
                        .jsonObject["features"]?.jsonArray?.map {
                        it.jsonObject.toEventEntity()
                    }.orEmpty()
                }
                else -> throw Exception()
            }
        }
    }

}


