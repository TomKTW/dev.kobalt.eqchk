package dev.kobalt.earthquakecheck.android.extension

import dev.kobalt.earthquakecheck.android.event.EventIntensity
import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.toEventIntensity(): EventIntensity? {
    return when (setScale(0, RoundingMode.HALF_EVEN)?.toInt() ?: 0) {
        1 -> EventIntensity.L1
        2 -> EventIntensity.L2
        3 -> EventIntensity.L3
        4 -> EventIntensity.L4
        5 -> EventIntensity.L5
        6 -> EventIntensity.L6
        7 -> EventIntensity.L7
        8 -> EventIntensity.L8
        9 -> EventIntensity.L9
        10 -> EventIntensity.L10
        11 -> EventIntensity.L11
        12 -> EventIntensity.L12
        else -> null
    }
}