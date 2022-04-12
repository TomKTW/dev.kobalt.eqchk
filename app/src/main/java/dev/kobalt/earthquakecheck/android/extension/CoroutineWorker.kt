package dev.kobalt.earthquakecheck.android.extension

import androidx.work.CoroutineWorker
import dev.kobalt.earthquakecheck.android.main.MainApplication

@Suppress("unused")
val CoroutineWorker.application: MainApplication
    get() = MainApplication.Native.instance