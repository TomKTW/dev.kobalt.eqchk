package dev.kobalt.eqchk.android.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import dev.kobalt.eqchk.android.base.BaseContext

open class FrameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

}