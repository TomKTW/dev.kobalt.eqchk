package dev.kobalt.eqchk.android.main

import dagger.hilt.android.HiltAndroidApp
import dev.kobalt.eqchk.android.base.BaseApplication
import dev.kobalt.eqchk.android.component.Preferences
import dev.kobalt.eqchk.android.component.WorkManager
import dev.kobalt.eqchk.android.view.MapView
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : BaseApplication() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        if (preferences.latestLoadEnabled == true) workManager.startLatestLoad()
        MapView.initialize(this)
    }

}