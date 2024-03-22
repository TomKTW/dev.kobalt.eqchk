package dev.kobalt.earthquakecheck.android.component

import android.Manifest
import android.annotation.SuppressLint
import androidx.core.content.getSystemService
import androidx.core.location.LocationManagerCompat
import androidx.core.os.CancellationSignal
import dev.kobalt.earthquakecheck.android.main.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LocationManager(val application: MainApplication) {

    private val locationManager get() = application.native.getSystemService<android.location.LocationManager>()!!
    private val scope = CoroutineScope(Dispatchers.IO)

    private var cancelSignal: CancellationSignal? = null

    fun cancel() {
        if (cancelSignal?.isCanceled != true) cancelSignal?.cancel()
        cancelSignal = null
    }

    @SuppressLint("MissingPermission")
    fun fetch() {
        if (application.arePermissionsGranted(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            if (cancelSignal?.isCanceled != true) {
                cancelSignal?.cancel()
            }
            cancelSignal = CancellationSignal()
            LocationManagerCompat.getCurrentLocation(
                locationManager, android.location.LocationManager.GPS_PROVIDER, cancelSignal,
                Dispatchers.IO.asExecutor()
            ) {
                scope.launch {
                    locationPointFlow.emit(
                        LocationPoint(it.latitude, it.longitude, it.altitude)
                    )
                }
            }
        }
    }

    val locationPointFlow = MutableSharedFlow<LocationPoint?>(1).apply {
        scope.launch { emit(null) }
    }

}


