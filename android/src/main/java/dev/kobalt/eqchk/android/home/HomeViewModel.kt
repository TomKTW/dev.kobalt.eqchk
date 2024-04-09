package dev.kobalt.eqchk.android.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobalt.eqchk.android.base.BaseViewModel
import dev.kobalt.eqchk.android.component.LocationManager
import dev.kobalt.eqchk.android.component.NotificationManager
import dev.kobalt.eqchk.android.component.Preferences
import dev.kobalt.eqchk.android.component.WorkManager
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.event.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationManager: LocationManager,
    private val workManager: WorkManager,
    private val notificationManager: NotificationManager,
    private val eventRepository: EventRepository,
    private val preferences: Preferences
) : BaseViewModel() {

    val locationPositionEnabled get() = preferences.locationPositionEnabled

    val latestLoadEnabled get() = preferences.latestLoadEnabled

    val locationPointFlow get() = locationManager.locationPointFlow

    val mapSelectionFlow = MutableSharedFlow<EventEntity?>(1).apply {
        viewModelScope.launch { emit(null) }
    }

    val mapOptionFlow = MutableSharedFlow<HomeMapView.LegendType>(1).apply {
        viewModelScope.launch { emit(HomeMapView.LegendType.None) }
    }

    val pageState = MutableSharedFlow<HomeFragment.Page>(1).apply {
        viewModelScope.launch { emit(HomeFragment.Page.Map) }
    }

    val dataState = MutableSharedFlow<List<EventEntity>>(1).apply {
        viewModelScope.launch { emit(emptyList()) }
    }

    val loadState = MutableSharedFlow<HomeLoadViewState>(1).apply {
        viewModelScope.launch { emit(HomeLoadViewState.Ready) }
    }

    val viewState = dataState.combine(locationPointFlow) { event, location ->
        HomeViewState(event)
    }

    fun load(forceReload: Boolean = false) {
        if (loadState.replayCache.firstOrNull() == HomeLoadViewState.Ready) {
            viewModelScope.launch(Dispatchers.IO) {
                loadState.emit(HomeLoadViewState.Loading)
                loadState.emit(execute(forceReload).also {
                    when (it) {
                        is HomeLoadViewState.Result.Success -> dataState.emit(it.data)
                        else -> dataState.emit(mutableListOf())
                    }
                })
                loadState.emit(HomeLoadViewState.Ready)
            }
        }
        if (preferences.locationPositionEnabled == true) {
            locationManager.fetch()
        }
    }

    fun toggleLocation() {
        preferences.apply {
            val newState = !(locationPositionEnabled ?: false)
            locationPositionEnabled = newState
            viewModelScope.launch(Dispatchers.IO) {
                locationManager.locationPointFlow.emit(null)
                if (newState) {
                    locationManager.fetch()
                } else {
                    locationManager.cancel()
                }
            }
        }
    }

    fun toggleNotifications() {
        preferences.apply {
            val newState = !(latestLoadEnabled ?: false)
            latestLoadEnabled = newState
            if (newState) {
                workManager.startLatestLoad()
            } else {
                workManager.cancelLatestLoad()
                notificationManager.hideLatest()
            }
        }
    }

    suspend fun execute(forceReload: Boolean): HomeLoadViewState.Result = runCatching {
        val list = eventRepository.selectList()
        if (list.isEmpty() || forceReload) {
            val presentDateTime = LocalDateTime.now(ZoneId.of("UTC"))
            val oneHourBeforeDateTime = presentDateTime.minusDays(1)
            return run {
                val newList = eventRepository.fetch(
                    minTimestamp = oneHourBeforeDateTime,
                    maxTimestamp = presentDateTime
                )
                eventRepository.reload(newList)
                preferences.lastListLoadTimestamp = LocalDateTime.now()
                HomeLoadViewState.Result.Success(eventRepository.selectList())
            }
        } else {
            HomeLoadViewState.Result.Success(eventRepository.selectList())
        }
    }.getOrElse { it.printStackTrace(); HomeLoadViewState.Result.Failure }

}

data class HomeViewState(
    val list: List<EventEntity>
)