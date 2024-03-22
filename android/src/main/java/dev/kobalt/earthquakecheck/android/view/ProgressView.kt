package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.ProgressBar
import dev.kobalt.earthquakecheck.android.base.BaseContext

class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.progressBarStyle
) : ProgressBar(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    init {
        indeterminateTintMode = PorterDuff.Mode.SRC_IN
    }

}