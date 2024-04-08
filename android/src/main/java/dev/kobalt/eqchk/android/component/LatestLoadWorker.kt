package dev.kobalt.eqchk.android.component

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.kobalt.eqchk.android.event.EventRepository
import java.time.LocalDateTime
import java.time.ZoneId

@HiltWorker
class LatestLoadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val eventRepository: EventRepository,
    private val preferences: Preferences,
    private val notificationManager: NotificationManager,
    private val workManager: WorkManager
) : CoroutineWorker(context, workerParams) {

    companion object {
        val name = "latestLoad"
    }

    override suspend fun doWork(): Result {
        return try {
            val presentDateTime = LocalDateTime.now(ZoneId.of("UTC"))
            val oneHourBeforeDateTime = presentDateTime.minusDays(1)
            val newList = eventRepository.fetch(
                minTimestamp = oneHourBeforeDateTime,
                maxTimestamp = presentDateTime
            )
            eventRepository.reload(newList)
            preferences.lastListLoadTimestamp = LocalDateTime.now()
            eventRepository.getLatestItem()?.let { event ->
                notificationManager.showLatest(event)
            }
            Result.success()
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure()
        } finally {
            workManager.startLatestLoad()
        }
    }

}