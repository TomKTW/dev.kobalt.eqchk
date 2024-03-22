package dev.kobalt.earthquakecheck.android.home

import dev.kobalt.earthquakecheck.android.event.EventEntity
import dev.kobalt.earthquakecheck.android.main.MainApplication
import java.time.LocalDateTime
import java.time.ZoneId

object HomeLoadUseCase {

    suspend fun execute(forceReload: Boolean): State.Result = runCatching {
        val list = MainApplication.Native.instance.eventRepository.selectList()
        if (list.isEmpty() || forceReload) {
            val presentDateTime = LocalDateTime.now(ZoneId.of("UTC"))
            val oneHourBeforeDateTime = presentDateTime.minusDays(1)
            return MainApplication.Native.instance.run {
                val newList = eventRepository.fetch(
                    minTimestamp = oneHourBeforeDateTime,
                    maxTimestamp = presentDateTime
                )
                eventRepository.reload(newList)
                preferences.lastListLoadTimestamp = LocalDateTime.now()
                State.Result.Success(eventRepository.selectList())
            }
        } else {
            State.Result.Success(MainApplication.Native.instance.eventRepository.selectList())
        }
    }.getOrElse { it.printStackTrace(); State.Result.Failure }

    sealed class State {
        object Ready : State()
        object Loading : State()
        sealed class Result : State() {
            class Success(val data: List<EventEntity>) : Result()
            object Failure : Result()
        }
    }

}