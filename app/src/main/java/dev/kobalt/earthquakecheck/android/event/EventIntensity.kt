package dev.kobalt.earthquakecheck.android.event

import androidx.annotation.ColorRes
import dev.kobalt.earthquakecheck.android.R

enum class EventIntensity(
    val number: String, val label: String, @ColorRes val color: Int
) {
    L1("I", "Not felt", R.color.intensity_1),
    L2("II", "Weak", R.color.intensity_2),
    L3("III", "Weak", R.color.intensity_3),
    L4("IV", "Light", R.color.intensity_4),
    L5("V", "Moderate", R.color.intensity_5),
    L6("VI", "Strong", R.color.intensity_6),
    L7("VII", "Very strong", R.color.intensity_7),
    L8("VIII", "Severe", R.color.intensity_8),
    L9("IX", "Violent", R.color.intensity_9),
    L10("X", "Extreme", R.color.intensity_10),
    L11("XI", "Extreme", R.color.intensity_11),
    L12("XII", "Extreme", R.color.intensity_12)
}