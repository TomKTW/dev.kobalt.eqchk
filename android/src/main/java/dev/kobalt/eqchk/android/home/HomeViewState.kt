package dev.kobalt.eqchk.android.home

data class HomeViewState(
    val isLoading: Boolean,
    val page: HomePage,
    val list: List<HomeEventEntity>
)