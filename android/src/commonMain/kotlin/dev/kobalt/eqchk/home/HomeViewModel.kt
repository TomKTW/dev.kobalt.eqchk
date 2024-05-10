package dev.kobalt.eqchk.home

import androidx.lifecycle.ViewModel
import dev.kobalt.eqchk.android.event.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val listFlow = eventRepository.items.map { list -> list.map { HomeEventEntity(it) } }
    private val isLoadingFlow = MutableStateFlow(false)
    private val pageFlow = MutableStateFlow(HomePage.Events)

    val viewState: Flow<HomeViewState> =
        MutableStateFlow(HomeViewState(false, HomePage.Events, emptyList(), EventFilter()))
            .combine(isLoadingFlow) { state, isLoading -> state.copy(isLoading = isLoading) }
            .combine(pageFlow) { state, page -> state.copy(page = page) }
            .combine(listFlow) { state, list -> state.copy(list = list) }

    init {
        refresh()
    }

    fun refresh() {
        GlobalScope.launch(Dispatchers.IO) {
            isLoadingFlow.emit(true)
            eventRepository.apply { fetch(EventFilter()).also { deleteAll(); insertAll(it) } }
            isLoadingFlow.emit(false)
        }
    }

    fun updatePage(page: HomePage) {
        GlobalScope.launch { pageFlow.emit(page) }
    }

}

