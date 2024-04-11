package dev.kobalt.eqchk.android.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobalt.eqchk.android.base.BaseViewModel
import dev.kobalt.eqchk.android.event.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : BaseViewModel() {

    private val listFlow = eventRepository.items.map { list -> list.map { HomeEventEntity(it) } }
    private val isLoadingFlow = MutableStateFlow(false)
    private val pageFlow = MutableStateFlow(HomePage.List)

    val viewState: Flow<HomeViewState> = MutableStateFlow(HomeViewState(false, HomePage.List, emptyList()))
        .combine(isLoadingFlow) { state, isLoading -> state.copy(isLoading = isLoading) }
        .combine(pageFlow) { state, page -> state.copy(page = page) }
        .combine(listFlow) { state, list -> state.copy(list = list) }

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingFlow.emit(true)
            eventRepository.apply { fetch().let { deleteAll(); insertAll(it) } }
            isLoadingFlow.emit(false)
        }
    }

    fun updatePage(page: HomePage) {
        viewModelScope.launch { pageFlow.emit(page) }
    }

}

