package dev.kobalt.eqchk.android.details

import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.extension.ifLet
import dev.kobalt.eqchk.android.extension.toEventIntensity
import dev.kobalt.eqchk.android.extension.toRelativeTimeString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmInline

data class DetailsViewState(
    val isLoading: Boolean,
    val event: DetailsEventEntity?
)

@JvmInline
value class DetailsEventEntity(
    private val entity: EventEntity
) {

    val id: String get() = entity.id

    val magnitude: String? get() = entity.magnitude?.scale(1)?.toString()

    val timeAgo: String?
        get() = entity.timestamp?.toRelativeTimeString(
            Clock.System.now().toLocalDateTime(TimeZone.UTC)
        )

    val timeStamp: String? get() = entity.timestamp?.toString() // TODO .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    val location: String? get() = entity.location

    val coordinates: String?
        get() = ifLet(entity.latitude, entity.longitude) { latitude, longitude ->
            "${latitude.toBigDecimal().scale(6)}, ${longitude.toBigDecimal().scale(6)}"
        }

    val depth: String? get() = entity.depth?.toBigDecimal()?.scale(0)?.toString()

    val tectonicSummary: String? get() = entity.tectonicSummary

    val impactSummary: String? get() = entity.impactSummary

    val estimatedIntensityScale get() = entity.estimatedIntensity?.toEventIntensity()

    val communityIntensityScale get() = entity.communityIntensity?.toEventIntensity()

    val detailsUri: String? get() = entity.detailsUrl

}