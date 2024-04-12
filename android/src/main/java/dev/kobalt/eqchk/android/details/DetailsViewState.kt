package dev.kobalt.eqchk.android.details

import android.net.Uri
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.extension.ifLet
import dev.kobalt.eqchk.android.extension.toEventIntensity
import dev.kobalt.eqchk.android.extension.toRelativeTimeString
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class DetailsViewState(
    val isLoading: Boolean,
    val event: DetailsEventEntity?
)

@JvmInline
value class DetailsEventEntity(
    private val entity: EventEntity
) {

    val id: String get() = entity.id

    val magnitude: String? get() = entity.magnitude?.setScale(1, RoundingMode.HALF_EVEN)?.toString()

    val timeAgo: String? get() = entity.timestamp?.toRelativeTimeString(LocalDateTime.now(ZoneId.of("UTC")))

    val timeStamp: String? get() = entity.timestamp?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    val location: String? get() = entity.location

    val coordinates: String?
        get() = ifLet(entity.latitude, entity.longitude) { latitude, longitude ->
            "${latitude.toBigDecimal().setScale(6, RoundingMode.HALF_EVEN)}, ${
                longitude.toBigDecimal().setScale(6, RoundingMode.HALF_EVEN)
            }"
        }

    val depth: String? get() = entity.depth?.toBigDecimal()?.setScale(0, RoundingMode.HALF_EVEN)?.toString()

    val tectonicSummary: String? get() = entity.tectonicSummary

    val impactSummary: String? get() = entity.impactSummary

    val estimatedIntensityScale get() = entity.estimatedIntensity?.toEventIntensity()

    val communityIntensityScale get() = entity.communityIntensity?.toEventIntensity()

    val detailsUri: Uri? get() = runCatching { Uri.parse(entity.detailsUrl) }.getOrNull()

}