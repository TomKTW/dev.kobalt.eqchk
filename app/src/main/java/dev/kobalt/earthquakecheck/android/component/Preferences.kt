package dev.kobalt.earthquakecheck.android.component

import androidx.preference.PreferenceManager
import dev.kobalt.earthquakecheck.android.extension.toLocalDateTime
import dev.kobalt.earthquakecheck.android.extension.toString
import dev.kobalt.earthquakecheck.android.main.MainApplication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Preferences(private val application: MainApplication) {

    val native get() = PreferenceManager.getDefaultSharedPreferences(application.native)!!

    var lastListLoadTimestamp: LocalDateTime?
        get() = get("lastListLoadTimestamp")
        set(value) {
            set("lastListLoadTimestamp", value)
        }

    var locationPositionEnabled: Boolean?
        get() = get("locationPositionEnabled")
        set(value) {
            set("locationPositionEnabled", value)
        }

    var latestLoadEnabled: Boolean?
        get() = get("latestLoadEnabled")
        set(value) {
            set("latestLoadEnabled", value)
        }

    inline operator fun <reified T> get(key: String): T? {
        return native.takeIf { it.contains(key) }?.let {
            when (T::class.java) {
                java.lang.Integer::class.java -> it.getInt(key, 0) as? T
                java.lang.Float::class.java -> it.getFloat(key, 0f) as? T
                java.lang.Boolean::class.java -> it.getBoolean(key, false) as? T
                java.lang.Long::class.java -> it.getLong(key, 0) as? T
                java.lang.String::class.java -> it.getString(key, null) as? T
                LocalDateTime::class.java -> (it.getString(key, null)
                    ?.toLocalDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) as? T
                else -> throw Exception("${T::class.java.name} cannot exist in preferences.")
            }
        }
    }

    inline operator fun <reified T> set(key: String, value: T?) {
        native.edit()?.let {
            if (value == null) {
                it.remove(key)
            } else {
                when (T::class.java) {
                    java.lang.Integer::class.java -> it.putInt(key, value as Int)
                    java.lang.Float::class.java -> it.putFloat(key, value as Float)
                    java.lang.Boolean::class.java -> it.putBoolean(key, value as Boolean)
                    java.lang.Long::class.java -> it.putLong(key, value as Long)
                    java.lang.String::class.java -> it.putString(key, value as String)
                    LocalDateTime::class.java -> (it.putString(
                        key,
                        (value as LocalDateTime).toString(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    ))
                    else -> throw Exception("${T::class.java.name} cannot be put in preferences.")
                }
            }
        }?.apply()
    }

}