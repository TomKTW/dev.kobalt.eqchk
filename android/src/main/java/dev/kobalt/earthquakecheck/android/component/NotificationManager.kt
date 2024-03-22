package dev.kobalt.earthquakecheck.android.component

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import dev.kobalt.earthquakecheck.android.R
import dev.kobalt.earthquakecheck.android.event.EventEntity
import dev.kobalt.earthquakecheck.android.main.MainActivity
import dev.kobalt.earthquakecheck.android.main.MainApplication
import java.math.RoundingMode

class NotificationManager(private val application: MainApplication) {

    val native get() = application.native.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createLatestChannel()
    }

    companion object {
        const val lastId = 0
        const val lastChannelId = "last"
    }

    private fun createLatestChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            native.createNotificationChannel(
                NotificationChannel(
                    lastChannelId,
                    application.getResourceString(R.string.notification_latest_channel_title),
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    enableVibration(true)
                    enableLights(true)
                    lightColor = R.color.white
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                })
        }
    }

    fun showLatest(event: EventEntity) {
        val resultIntent = Intent(application.native, MainActivity::class.java).apply {
            putExtra(MainActivity.lastIdKey, event.id)
            flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resultPendingIntent = PendingIntent.getActivity(
            application.native,
            0,
            resultIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        native.notify(
            lastId,
            NotificationCompat.Builder(application.native, lastChannelId)
                .setContentTitle(
                    application.getResourceString(
                        R.string.notification_latest_title,
                        event.magnitude?.setScale(1, RoundingMode.HALF_EVEN).toString()
                    )
                )
                .setContentText(event.location)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_track_changes_24)
                .build()
        )
    }

    fun hideLatest() {
        native.cancel(lastId)
    }

}