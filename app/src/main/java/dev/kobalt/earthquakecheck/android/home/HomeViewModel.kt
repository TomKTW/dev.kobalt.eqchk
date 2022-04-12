package dev.kobalt.earthquakecheck.android.home

import androidx.lifecycle.viewModelScope
import dev.kobalt.earthquakecheck.android.base.BaseViewModel
import dev.kobalt.earthquakecheck.android.event.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val locationPointFlow get() = application.locationManager.locationPointFlow

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

    val loadState = MutableSharedFlow<HomeLoadUseCase.State>(1).apply {
        viewModelScope.launch { emit(HomeLoadUseCase.State.Ready) }
    }

    fun load(forceReload: Boolean = false) {
        if (loadState.replayCache.firstOrNull() == HomeLoadUseCase.State.Ready) {
            viewModelScope.launch(Dispatchers.IO) {
                loadState.emit(HomeLoadUseCase.State.Loading)
                loadState.emit(HomeLoadUseCase.execute(forceReload).also {
                    when (it) {
                        is HomeLoadUseCase.State.Result.Success -> dataState.emit(it.data)
                        else -> dataState.emit(mutableListOf())
                    }
                })
                loadState.emit(HomeLoadUseCase.State.Ready)
            }
        }
        if (application.preferences.locationPositionEnabled == true) {
            application.locationManager.fetch()
        }
    }

    fun toggleLocation() {
        application.preferences.apply {
            val newState = !(locationPositionEnabled ?: false)
            locationPositionEnabled = newState
            viewModelScope.launch(Dispatchers.IO) {
                application.locationManager.locationPointFlow.emit(null)
                if (newState) {
                    application.locationManager.fetch()
                } else {
                    application.locationManager.cancel()
                }
            }
        }
    }

    fun toggleNotifications() {
        application.preferences.apply {
            val newState = !(latestLoadEnabled ?: false)
            latestLoadEnabled = newState
            if (newState) {
                application.workManager.startLatestLoad()
            } else {
                application.workManager.cancelLatestLoad()
                application.notificationManager.hideLatest()
            }
        }
    }

}


