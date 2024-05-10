package dev.kobalt.eqchk.home

import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.extension.toRelativeTimeString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmInline

@JvmInline
value class HomeEventEntity(private val entity: EventEntity) {

    val id: String get() = entity.id

    val magnitude: String get() = entity.magnitude?.scale(1)?.toString() ?: "-.-"

    val time: String
        get() = entity.timestamp?.toRelativeTimeString(Clock.System.now().toLocalDateTime(TimeZone.UTC)) ?: "--"

    val location: String get() = entity.location ?: "Somewhere on Earth"

}