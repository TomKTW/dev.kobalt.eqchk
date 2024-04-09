package dev.kobalt.eqchk.android.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseContext {

    override fun requestContext(): Context = requireContext().applicationContext

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext())
    }

    val composeView get() = view as? ComposeView

}