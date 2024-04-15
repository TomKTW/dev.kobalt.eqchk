package dev.kobalt.eqchk.android.home

import java.time.Instant

data class HomeViewState(
    val isLoading: Boolean,
    val page: HomePage,
    val list: List<HomeEventEntity>,
    val filter: EventFilter
)

data class EventFilter(
    val magnitudeMin: Int? = null,
    val magnitudeMax: Int? = null,
    val timestampMin: Instant? = null,
    val timestampMax: Instant? = null,
    val locationLatitude: Double? = null,
    val locationLongitude: Double? = null,
    val locationRange: Double? = null
)