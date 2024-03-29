package dev.kobalt.eqchk.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dev.kobalt.eqchk.android.base.BaseContext

open class RecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    abstract class Adapter<T : Holder> : RecyclerView.Adapter<T>()

    abstract class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    abstract class BindingHolder<T : ViewBinding>(val binding: T) : Holder(binding.root)

}