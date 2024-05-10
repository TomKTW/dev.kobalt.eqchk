package dev.kobalt.eqchk.android.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kobalt.eqchk.android.event.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailsViewModel constructor(
    //private val savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val id: String get() = "0"// TODO checkNotNull(savedStateHandle["id"])

    private val isLoadingFlow = MutableStateFlow(false)
    private val eventFlow = eventRepository.getEvent(id).map { event -> event?.let { DetailsEventEntity(it) } }

    val viewState = MutableStateFlow(DetailsViewState(false, null))
        .combine(isLoadingFlow) { state, isLoading -> state.copy(isLoading = isLoading) }
        .combine(eventFlow) { state, event -> state.copy(event = event) }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingFlow.emit(true)
            eventRepository.apply { fetchById(id)?.also { delete(it); insert(it) } }
            isLoadingFlow.emit(false)
        }
    }

}