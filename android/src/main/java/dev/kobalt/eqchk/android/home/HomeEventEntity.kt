package dev.kobalt.eqchk.android.home

import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.extension.toRelativeTimeString
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneId

@JvmInline
value class HomeEventEntity(private val entity: EventEntity) {

    val id: String get() = entity.id

    val magnitude: String get() = entity.magnitude?.setScale(1, RoundingMode.HALF_EVEN)?.toString() ?: "-.-"

    val time: String get() = entity.timestamp?.toRelativeTimeString(LocalDateTime.now(ZoneId.of("UTC"))) ?: "--"

    val location: String get() = entity.location ?: "Somewhere on Earth"

}