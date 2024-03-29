package dev.kobalt.eqchk.android.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import dev.kobalt.eqchk.android.base.BaseContext

class VerticalScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

}