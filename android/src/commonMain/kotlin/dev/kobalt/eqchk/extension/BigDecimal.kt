package dev.kobalt.eqchk.android.extension

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import dev.kobalt.eqchk.android.event.EventIntensity

fun BigDecimal.toEventIntensity(): EventIntensity? {
    return when (scale(0).intValue()) {
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