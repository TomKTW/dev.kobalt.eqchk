package dev.kobalt.eqchk.android.search

import dev.kobalt.eqchk.android.event.EventEntity

sealed class SearchSubmitViewState {
    object Ready : SearchSubmitViewState()
    object Loading : SearchSubmitViewState()
    sealed class Result : SearchSubmitViewState() {
        class Success(val data: List<EventEntity>) : Result()
        object Failure : Result()
    }
}