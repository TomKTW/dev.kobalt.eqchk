package dev.kobalt.eqchk.android.component

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.kobalt.eqchk.android.extension.application
import java.time.LocalDateTime
import java.time.ZoneId

class LatestLoadWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    companion object {
        val name = "latestLoad"
    }

    override suspend fun doWork(): Result {
        return try {
            val presentDateTime = LocalDateTime.now(ZoneId.of("UTC"))
            val oneHourBeforeDateTime = presentDateTime.minusDays(1)
            val newList = application.eventRepository.fetch(
                minTimestamp = oneHourBeforeDateTime,
                maxTimestamp = presentDateTime
            )
            application.eventRepository.reload(newList)
            application.preferences.lastListLoadTimestamp = LocalDateTime.now()
            application.eventRepository.getLatestItem()?.let { event ->
                application.notificationManager.showLatest(event)
            }
            Result.success()
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure()
        } finally {
            application.workManager.startLatestLoad()
        }
    }

}