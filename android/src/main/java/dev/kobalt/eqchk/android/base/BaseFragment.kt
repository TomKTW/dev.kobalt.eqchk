package dev.kobalt.eqchk.android.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : ViewBinding> : KeyedFragment(), BaseContext {

    override fun requestContext(): Context = requireContext().applicationContext

    private var binding: V? = null
    val viewBinding: V? get() = binding

    private val viewBindingClass =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
    private val method = viewBindingClass.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    @Suppress("UNCHECKED_CAST")
    protected open fun createBindingInstance(inflater: LayoutInflater, container: ViewGroup?): V {
        return method.invoke(null, inflater, container, false) as V
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createBindingInstance(inflater, container).also { binding = it }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    val viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope

}