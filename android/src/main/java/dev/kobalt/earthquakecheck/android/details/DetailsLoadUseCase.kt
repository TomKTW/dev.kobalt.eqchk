package dev.kobalt.earthquakecheck.android.details

import dev.kobalt.earthquakecheck.android.event.EventEntity
import dev.kobalt.earthquakecheck.android.main.MainApplication

object DetailsLoadUseCase {

    suspend fun execute(
        id: String
    ): State.Result = runCatching {
        /*EventRepository.selectItem(id)?.let {
            State.Result.Success(it)
        } ?: State.Result.Failure */
        return State.Result.Success(
            MainApplication.Native.instance.eventRepository.fetchItem(id) ?: throw Exception()
        )
        /*val url = "https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=${id}&format=geojson"
        MainApplication.instance.httpClient.get<HttpStatement>(url).execute().let { response ->
            when {
                response.status.isSuccess() -> {
                    val item = response.readText().toJsonElement().jsonObject.toEventEntity()
                    State.Result.Success(item)
                }
                else -> State.Result.Failure
            }
        }*/
    }.getOrElse { it.printStackTrace(); State.Result.Failure }

    sealed class State {
        object Ready : State()
        object Loading : State()
        sealed class Result : State() {
            class Success(val data: EventEntity) : Result()
            object Failure : Result()
        }
    }

}