package dev.kobalt.earthquakecheck.android.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.time.LocalDateTime

@Parcelize
data class EventEntity(
    val id: String?,
    val location: String?,
    val timestamp: LocalDateTime?,
    val estimatedIntensity: BigDecimal?,
    val communityIntensity: BigDecimal?,
    val communityResponseCount: Int?,
    val magnitude: BigDecimal?,
    val latitude: Double?,
    val longitude: Double?,
    val depth: Double?,
    val detailsUrl: String?,
    val tectonicSummary: String?,
    val impactSummary: String?
) : Parcelable

