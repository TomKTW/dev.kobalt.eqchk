package dev.kobalt.earthquakecheck.android.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class BaseView<V : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    private lateinit var binding: V

    val viewBinding: V get() = binding

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

    init {
        onInit()
    }

    private fun onInit() {
        binding = createBindingInstance(LayoutInflater.from(context), null)
        addView(binding.root)
    }

}