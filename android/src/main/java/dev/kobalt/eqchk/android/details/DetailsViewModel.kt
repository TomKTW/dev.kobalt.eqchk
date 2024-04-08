package dev.kobalt.eqchk.android.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobalt.eqchk.android.component.LocationManager
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.event.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val locationManager: LocationManager,
    private val eventRepository: EventRepository
) : ViewModel() {

    val locationPointFlow get() = locationManager.locationPointFlow

    val pageState = MutableSharedFlow<DetailsFragment.Page>(1).apply {
        viewModelScope.launch { emit(DetailsFragment.Page.Info) }
    }

    val dataState = MutableSharedFlow<EventEntity?>(1).apply {
        viewModelScope.launch { emit(null) }
    }

    val loadState = MutableSharedFlow<DetailsLoadViewState>(1).apply {
        viewModelScope.launch { emit(DetailsLoadViewState.Ready) }
    }

    fun load(id: String) {
        if (loadState.replayCache.firstOrNull() == DetailsLoadViewState.Ready) {
            viewModelScope.launch(Dispatchers.IO) {
                loadState.emit(DetailsLoadViewState.Loading)
                loadState.emit(execute(id).also {
                    when (it) {
                        is DetailsLoadViewState.Result.Success -> dataState.emit(it.data)
                        else -> dataState.emit(null)
                    }
                })
            }
        }
    }

    private suspend fun execute(
        id: String
    ): DetailsLoadViewState.Result = runCatching {
        return DetailsLoadViewState.Result.Success(eventRepository.fetchItem(id)!!)
    }.getOrElse {
        it.printStackTrace(); DetailsLoadViewState.Result.Failure
    }

}

