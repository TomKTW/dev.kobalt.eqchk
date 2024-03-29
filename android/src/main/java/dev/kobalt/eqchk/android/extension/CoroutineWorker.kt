package dev.kobalt.eqchk.android.extension

import androidx.work.CoroutineWorker
import dev.kobalt.eqchk.android.main.MainApplication

@Suppress("unused")
val CoroutineWorker.application: MainApplication
    get() = MainApplication.Native.instance