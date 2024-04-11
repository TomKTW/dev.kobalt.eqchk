package dev.kobalt.eqchk.android.home

import androidx.annotation.StringRes
import dev.kobalt.eqchk.android.R

enum class HomePage(@StringRes val titleResId: Int) {
    Map(R.string.home_map),
    List(R.string.home_list),
    Options(R.string.home_options)
}