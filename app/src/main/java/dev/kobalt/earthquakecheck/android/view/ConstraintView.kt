package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import dev.kobalt.earthquakecheck.android.base.BaseContext

open class ConstraintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

}