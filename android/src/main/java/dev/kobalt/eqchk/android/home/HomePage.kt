package dev.kobalt.eqchk.android.home

import androidx.annotation.StringRes
import dev.kobalt.eqchk.android.R

enum class HomePage(@StringRes val titleResId: Int) {
    Events(R.string.home_events),
    Map(R.string.home_map)
}