package dev.kobalt.earthquakecheck.android.main

import android.content.Context
import dev.kobalt.earthquakecheck.android.base.BaseApplication
import dev.kobalt.earthquakecheck.android.base.BaseContext
import dev.kobalt.earthquakecheck.android.component.LocationManager
import dev.kobalt.earthquakecheck.android.component.NotificationManager
import dev.kobalt.earthquakecheck.android.component.Preferences
import dev.kobalt.earthquakecheck.android.component.WorkManager
import dev.kobalt.earthquakecheck.android.event.EventRepository
import dev.kobalt.earthquakecheck.android.view.MapView
import io.ktor.client.*
import io.ktor.client.engine.android.*

class MainApplication(val native: Native) : BaseContext {

    override fun requestContext(): Context = native

    class Native : BaseApplication() {

        companion object {
            lateinit var instance: MainApplication private set
        }

        override fun onCreate() {
            super.onCreate()
            instance = MainApplication(this)
            MapView.initialize(this)
        }

    }

    val httpClient = HttpClient(Android) { expectSuccess = false }

    val locationManager = LocationManager(this)

    val preferences = Preferences(this)

    val workManager = WorkManager(this)

    val notificationManager = NotificationManager(this)

    val eventRepository = EventRepository(this)

    init {
        if (preferences.latestLoadEnabled == true) workManager.startLatestLoad()
    }

}


