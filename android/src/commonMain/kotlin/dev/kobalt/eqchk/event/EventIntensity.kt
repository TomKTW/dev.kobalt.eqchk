package dev.kobalt.eqchk.android.event

enum class EventIntensity(
    val number: String, val label: String, val color: Int
) {
    L1("I", "Not felt", 1),
    L2("II", "Weak", 2),
    L3("III", "Weak", 3),
    L4("IV", "Light", 4),
    L5("V", "Moderate", 5),
    L6("VI", "Strong", 6),
    L7("VII", "Very strong", 7),
    L8("VIII", "Severe", 8),
    L9("IX", "Violent", 9),
    L10("X", "Extreme", 10),
    L11("XI", "Extreme", 11),
    L12("XII", "Extreme", 12)
}