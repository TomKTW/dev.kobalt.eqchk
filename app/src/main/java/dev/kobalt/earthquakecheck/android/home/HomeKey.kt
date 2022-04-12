package dev.kobalt.earthquakecheck.android.home

import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeKey(private val placeholder: String = "") : DefaultFragmentKey() {
    override fun instantiateFragment() = HomeFragment()
}