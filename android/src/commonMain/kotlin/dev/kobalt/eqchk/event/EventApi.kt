package dev.kobalt.eqchk.android.event

import dev.kobalt.eqchk.android.extension.toJsonElement
import dev.kobalt.eqchk.home.EventFilter
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class EventApi(
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

    suspend fun fetchList(
        filter: EventFilter,
    ): List<EventEntity> {
        httpClient.prepareGet(HttpRequestBuilder().apply {
            url {
                protocol = URLProtocol.HTTPS
                host = "earthquake.usgs.gov"
                encodedPath = "/fdsnws/event/1/query"
                parameters.apply {
                    this["format"] = "geojson"
                    filter.magnitudeMin?.let { this["minmagnitude"] = it.toString() }
                    filter.magnitudeMin?.let { this["maxmagnitude"] = it.toString() }
                    /*minEstimatedIntensity?.let { this["minmmi"] = it.toString() }
                    maxEstimatedIntensity?.let { this["maxmmi"] = it.toString() }
                    minCommunityIntensity?.let { this["mincdi"] = it.toString() }
                    maxCommunityIntensity?.let { this["maxcdi"] = it.toString() }
                    minDepth?.let { this["mindepth"] = it.toString() }
                    maxDepth?.let { this["maxdepth"] = it.toString() }*/
                    filter.timestampMin?.let {
                        this["starttime"] =
                            "" // TODO DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC).format(it)
                    }
                    filter.timestampMax?.let {
                        this["endtime"] = "" // TODO DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC).format(it)
                    }
                    filter.locationLatitude?.let { this["latitude"] = it.toString() }
                    filter.locationLongitude?.let { this["longitude"] = it.toString() }
                    filter.locationRange?.let { this["maxradiuskm"] = it.toString() }
                    this["limit"] = "100"
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