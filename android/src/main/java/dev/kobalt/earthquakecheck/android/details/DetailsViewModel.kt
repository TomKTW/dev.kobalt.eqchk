package dev.kobalt.earthquakecheck.android.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kobalt.earthquakecheck.android.event.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    val pageState = MutableSharedFlow<DetailsFragment.Page>(1).apply {
        viewModelScope.launch { emit(DetailsFragment.Page.Info) }
    }

    val dataState = MutableSharedFlow<EventEntity?>(1).apply {
        viewModelScope.launch { emit(null) }
    }

    val loadState = MutableSharedFlow<DetailsLoadUseCase.State>(1).apply {
        viewModelScope.launch { emit(DetailsLoadUseCase.State.Ready) }
    }

    fun load(id: String) {
        if (loadState.replayCache.firstOrNull() == DetailsLoadUseCase.State.Ready) {
            viewModelScope.launch(Dispatchers.IO) {
                loadState.emit(DetailsLoadUseCase.State.Loading)
                loadState.emit(DetailsLoadUseCase.execute(id).also {
                    when (it) {
                        is DetailsLoadUseCase.State.Result.Success -> dataState.emit(it.data)
                        else -> dataState.emit(null)
                    }
                })
            }
        }
    }

}

