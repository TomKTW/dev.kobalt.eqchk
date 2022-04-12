package dev.kobalt.earthquakecheck.android.base

import androidx.lifecycle.ViewModel
import dev.kobalt.earthquakecheck.android.main.MainApplication

open class BaseViewModel : ViewModel() {

    val application get() = MainApplication.Native.instance

}