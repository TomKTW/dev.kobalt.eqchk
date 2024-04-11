package dev.kobalt.eqchk.android.search

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobalt.eqchk.android.base.BaseViewModel
import dev.kobalt.eqchk.android.event.EventRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : BaseViewModel() {

    /*
    var formMinMagnitude: Double? = null
    var formMaxMagnitude: Double? = null
    var formMinEstimatedIntensity: Double? = null
    var formMaxEstimatedIntensity: Double? = null
    var formMinCommunityIntensity: Double? = null
    var formMaxCommunityIntensity: Double? = null
    var formMinDepth: Double? = null
    var formMaxDepth: Double? = null
    var formMinTimestamp: LocalDateTime? = null
    var formMaxTimestamp: LocalDateTime? = null

    val dataState = MutableSharedFlow<List<EventEntity>>(1).apply {
        viewModelScope.launch { emit(emptyList()) }
    }

    val loadState = MutableSharedFlow<SearchSubmitViewState>(1).apply {
        viewModelScope.launch { emit(SearchSubmitViewState.Ready) }
    }

    val viewState = MutableSharedFlow<Any>(1).apply {

    }

    fun load() {
        if (loadState.replayCache.firstOrNull() == SearchSubmitViewState.Ready) {
            viewModelScope.launch(Dispatchers.IO) {
                dataState.emit(emptyList())
                loadState.emit(SearchSubmitViewState.Loading)
                loadState.emit(
                    execute(
                    formMinMagnitude?.toInt(),
                    formMaxMagnitude?.toInt(),
                    formMinEstimatedIntensity?.toInt(),
                    formMaxEstimatedIntensity?.toInt(),
                    formMinCommunityIntensity?.toInt(),
                    formMaxCommunityIntensity?.toInt(),
                    formMinDepth?.toInt(),
                    formMaxDepth?.toInt(),
                    formMinTimestamp,
                    formMaxTimestamp,
                    null,
                    null,
                    null
                ).also {
                    when (it) {
                        is SearchSubmitViewState.Result.Success -> dataState.emit(it.data)
                        else -> dataState.emit(mutableListOf())
                    }
                })
                loadState.emit(SearchSubmitViewState.Ready)
            }
        }
    }


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
    ): SearchSubmitViewState.Result = runCatching {
        SearchSubmitViewState.Result.Success(
            eventRepository.fetch(
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
    }.getOrElse { it.printStackTrace(); SearchSubmitViewState.Result.Failure }


     */
}




