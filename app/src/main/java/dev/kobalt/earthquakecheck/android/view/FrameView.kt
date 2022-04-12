package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import dev.kobalt.earthquakecheck.android.base.BaseContext

open class FrameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

}