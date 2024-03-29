package dev.kobalt.eqchk.android.search

import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchKey(private val placeholder: String = "") : DefaultFragmentKey() {
    override fun instantiateFragment() = SearchFragment()
}