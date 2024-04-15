package dev.kobalt.eqchk.android.event

import dev.kobalt.eqchk.android.extension.toJsonElement
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun fetchItem(id: String?): EventEntity? {
        httpClient.prepareGet(HttpRequestBuilder().apply {
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
                    response.bodyAsText().toJsonElement().jsonObject.toEventEntity()
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
        httpClient.prepareGet(HttpRequestBuilder().apply {
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
                    return response.bodyAsText().toJsonElement()
                        .jsonObject["features"]?.jsonArray?.map {
                        it.jsonObject.toEventEntity()
                    }.orEmpty()
                }

                else -> throw Exception()
            }
        }
    }

}