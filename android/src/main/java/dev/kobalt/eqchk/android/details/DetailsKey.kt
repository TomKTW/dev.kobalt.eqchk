package dev.kobalt.eqchk.android.details

import android.os.Bundle
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsKey(private val uid: String) :
    DefaultFragmentKey() { // generate reliable `toString()` for no-args data class
    override fun instantiateFragment() = DetailsFragment().apply {
        arguments = (arguments ?: Bundle()).also { bundle ->
            bundle.putString(DetailsFragment.uidKey, uid)
        }
    }
}