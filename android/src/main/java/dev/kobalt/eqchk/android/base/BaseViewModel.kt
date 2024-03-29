package dev.kobalt.eqchk.android.base

import androidx.lifecycle.ViewModel
import dev.kobalt.eqchk.android.main.MainApplication

open class BaseViewModel : ViewModel() {

    val application get() = MainApplication.Native.instance

}