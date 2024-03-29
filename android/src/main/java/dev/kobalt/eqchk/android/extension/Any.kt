package dev.kobalt.eqchk.android.extension

inline fun <T1 : Any, T2 : Any, R : Any> ifLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? =
    if (p1 != null && p2 != null) block(p1, p2) else null
