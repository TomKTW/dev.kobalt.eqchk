package dev.kobalt.earthquakecheck.android.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kobalt.earthquakecheck.android.event.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SearchViewModel : ViewModel() {

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

    val loadState = MutableSharedFlow<SearchSubmitUseCase.State>(1).apply {
        viewModelScope.launch { emit(SearchSubmitUseCase.State.Ready) }
    }

    fun load() {
        if (loadState.replayCache.firstOrNull() == SearchSubmitUseCase.State.Ready) {
            viewModelScope.launch(Dispatchers.IO) {
                dataState.emit(emptyList())
                loadState.emit(SearchSubmitUseCase.State.Loading)
                loadState.emit(SearchSubmitUseCase.execute(
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
                        is SearchSubmitUseCase.State.Result.Success -> dataState.emit(it.data)
                        else -> dataState.emit(mutableListOf())
                    }
                })
                loadState.emit(SearchSubmitUseCase.State.Ready)
            }
        }
    }

}




