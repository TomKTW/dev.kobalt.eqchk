package dev.kobalt.eqchk.android.search

import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.main.MainApplication
import java.time.LocalDateTime

object SearchSubmitUseCase {

    suspend fun execute(
        minMagnitude: Int?,
        maxMagnitude: Int?,
        minEstimatedIntensity: Int?,
        maxEstimatedIntensity: Int?,
        minCommunityIntensity: Int?,
        maxCommunityIntensity: Int?,
        minDepth: Int?,
        maxDepth: Int?,
        minTimestamp: LocalDateTime?,
        maxTimestamp: LocalDateTime?,
        latitude: Double?,
        longitude: Double?,
        range: Double?
    ): State.Result = runCatching {
        State.Result.Success(
            MainApplication.Native.instance.eventRepository.fetch(
                minMagnitude,
                maxMagnitude,
                minEstimatedIntensity,
                maxEstimatedIntensity,
                minCommunityIntensity,
                maxCommunityIntensity,
                minDepth,
                maxDepth,
                minTimestamp,
                maxTimestamp,
                latitude,
                longitude,
                range
            )
        )
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