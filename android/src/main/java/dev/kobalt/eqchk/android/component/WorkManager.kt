package dev.kobalt.eqchk.android.component

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dev.kobalt.eqchk.android.base.BaseContext
import java.util.concurrent.TimeUnit

class WorkManager(private val context: Context) : BaseContext {

    override fun requestContext(): Context = context.applicationContext

    val native get() = WorkManager.getInstance(context)

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