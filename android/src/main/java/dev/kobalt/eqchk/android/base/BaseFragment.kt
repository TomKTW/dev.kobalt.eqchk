package dev.kobalt.eqchk.android.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<V : ViewBinding> : Fragment(), BaseContext {

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

    fun <T> Flow<T>.collectOnStartedLifecycleState(result: (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    result.invoke(it)
                }
            }
        }
    }

}