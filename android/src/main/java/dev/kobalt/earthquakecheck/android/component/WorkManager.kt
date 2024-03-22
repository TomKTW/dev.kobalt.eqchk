package dev.kobalt.earthquakecheck.android.component

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dev.kobalt.earthquakecheck.android.main.MainApplication
import java.util.concurrent.TimeUnit

class WorkManager(private val application: MainApplication) {

    val native get() = WorkManager.getInstance(application.native)

    fun cancelLatestLoad() {
        native.cancelUniqueWork(LatestLoadWorker.name)
    }

    fun startLatestLoad() {
        native.enqueueUniqueWork(
            LatestLoadWorker.name,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.Builder(LatestLoadWorker::class.java)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .build()
        )
    }

}