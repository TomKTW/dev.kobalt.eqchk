package dev.kobalt.eqchk.android.event

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
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
)

