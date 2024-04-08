package dev.kobalt.eqchk.android.details

import dev.kobalt.eqchk.android.event.EventEntity

sealed class DetailsLoadViewState {
    object Ready : DetailsLoadViewState()
    object Loading : DetailsLoadViewState()
    sealed class Result : DetailsLoadViewState() {
        class Success(val data: EventEntity) : Result()
        object Failure : Result()
    }
}