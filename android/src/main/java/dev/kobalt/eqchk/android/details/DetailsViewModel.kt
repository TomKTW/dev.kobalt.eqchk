package dev.kobalt.eqchk.android.details

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.component.LocationConverter
import dev.kobalt.eqchk.android.component.LocationManager
import dev.kobalt.eqchk.android.component.LocationPoint
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.event.EventRepository
import dev.kobalt.eqchk.android.extension.ifLet
import dev.kobalt.eqchk.android.extension.toEventIntensity
import dev.kobalt.eqchk.android.extension.toRelativeTimeString
import dev.kobalt.eqchk.android.extension.toSpannedHtml
import dev.kobalt.eqchk.android.main.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mainApplication: MainApplication,
    private val locationManager: LocationManager,
    private val eventRepository: EventRepository,
) : ViewModel() {

    val locationPointFlow get() = locationManager.locationPointFlow

    val dataState = MutableSharedFlow<EventEntity?>(1).apply {
        viewModelScope.launch { emit(null) }
    }

    val loadState = MutableSharedFlow<DetailsLoadViewState>(1).apply {
        viewModelScope.launch { emit(DetailsLoadViewState.Ready) }
    }

    val viewState = dataState.combine(locationPointFlow) { event, location ->
        if (event == null) return@combine null

        val magnitude = event.magnitude?.setScale(1, RoundingMode.HALF_EVEN)?.toString() ?: "-.-"
        val time = event.timestamp?.toRelativeTimeString(LocalDateTime.now(ZoneId.of("UTC"))) ?: ""
        val distanceValue = if (event.latitude != null && event.longitude != null && event.depth != null) {
            location?.distanceFrom(
                LocationPoint(event.latitude, event.longitude, -event.depth)
            )?.toBigDecimal()?.divide(
                BigDecimal(1000)
            )?.setScale(0, RoundingMode.HALF_EVEN)
        } else null
        val location = event.location ?: "-"
        val isDistanceShown = distanceValue != null
        val distance = when {
            distanceValue == null -> {
                "-"
            }

            distanceValue < BigDecimal.ONE -> {
                "<1 km"
            }

            else -> distanceValue.setScale(0, RoundingMode.HALF_EVEN)?.let { "$it km" }
        } ?: ""
        val coordinates = ifLet(event.latitude, event.longitude) { latitude, longitude ->
            LocationConverter.locationToStringDMS(latitude, longitude, 1)
        } ?: ""
        val depth = event.depth?.toBigDecimal()?.setScale(0, RoundingMode.HALF_EVEN)
            ?.let { "$it ${mainApplication.getResourceString(R.string.kilometers)}" } ?: "-"

        val tectonicSummary = event.tectonicSummary?.toSpannedHtml()
        val impactSummary = event.impactSummary?.toSpannedHtml()
        val estimatedIntensityScale = event.estimatedIntensity?.toEventIntensity()
        val estimatedIntensityValue =
            estimatedIntensityScale?.label ?: mainApplication.getResourceString(R.string.not_available)
        val communityIntensityScale = event.communityIntensity?.toEventIntensity()
        val communityIntensityValue =
            communityIntensityScale?.label ?: mainApplication.getResourceString(R.string.not_available)

        val uri = runCatching { Uri.parse(event.detailsUrl) }.getOrNull()
        DetailsViewState(
            magnitude = magnitude,
            time = time,
            location = location,
            isDistanceShown = isDistanceShown,
            distance = distance,
            coordinates = coordinates,
            depth = depth,
            tectonicSummary = tectonicSummary,
            impactSummary = impactSummary,
            estimatedIntensityScale = estimatedIntensityScale,
            estimatedIntensityValue = estimatedIntensityValue,
            communityIntensityScale = communityIntensityScale,
            communityIntensityValue = communityIntensityValue,
            detailsUri = uri
        )

    }

    fun load(id: String) {
        if (loadState.replayCache.firstOrNull() == DetailsLoadViewState.Ready) {
            viewModelScope.launch(Dispatchers.IO) {
                loadState.emit(DetailsLoadViewState.Loading)
                loadState.emit(execute(id).also {
                    when (it) {
                        is DetailsLoadViewState.Result.Success -> dataState.emit(it.data)
                        else -> dataState.emit(null)
                    }
                })
            }
        }
    }

    private suspend fun execute(
        id: String
    ): DetailsLoadViewState.Result = runCatching {
        return DetailsLoadViewState.Result.Success(eventRepository.fetchItem(id)!!)
    }.getOrElse {
        it.printStackTrace(); DetailsLoadViewState.Result.Failure
    }

}

