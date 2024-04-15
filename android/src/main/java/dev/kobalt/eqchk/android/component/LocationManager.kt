package dev.kobalt.eqchk.android.component

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.location.LocationManagerCompat
import androidx.core.os.CancellationSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LocationManager(
    private val context: Context
) {

    private val native get() = context.getSystemService<android.location.LocationManager>()!!
    private val scope = CoroutineScope(Dispatchers.IO)

    private var cancelSignal: CancellationSignal? = null

    fun cancel() {
        if (cancelSignal?.isCanceled != true) cancelSignal?.cancel()
        cancelSignal = null
    }

    @SuppressLint("MissingPermission")
    fun fetch() {
        if (listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
        ) {
            if (cancelSignal?.isCanceled != true) {
                cancelSignal?.cancel()
            }
            cancelSignal = CancellationSignal()
            LocationManagerCompat.getCurrentLocation(
                native, android.location.LocationManager.GPS_PROVIDER, cancelSignal,
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


