package dev.kobalt.eqchk.android.home

import dev.kobalt.eqchk.android.event.EventEntity

sealed class HomeLoadViewState {
    object Ready : HomeLoadViewState()
    object Loading : HomeLoadViewState()
    sealed class Result : HomeLoadViewState() {
        class Success(val data: List<EventEntity>) : Result()
        object Failure : Result()
    }
}