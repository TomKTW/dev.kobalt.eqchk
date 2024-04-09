package dev.kobalt.eqchk.android.details

import android.net.Uri
import android.text.Spanned
import dev.kobalt.eqchk.android.event.EventIntensity

data class DetailsViewState(
    val magnitude: String,
    val time: String,
    val location: String,
    val isDistanceShown: Boolean,
    val distance: String,
    val coordinates: String,
    val depth: String,
    val tectonicSummary: Spanned?,
    val impactSummary: Spanned?,
    val estimatedIntensityScale: EventIntensity?,
    val estimatedIntensityValue: String,
    val communityIntensityScale: EventIntensity?,
    val communityIntensityValue: String,
    val detailsUri: Uri?
)