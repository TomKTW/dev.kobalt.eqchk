package dev.kobalt.eqchk.android.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import dev.kobalt.eqchk.android.base.BaseContext

open class LabelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    var fontSize: Int
        get() = sp(textSize.toInt())
        set(value) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, value.toFloat())
        }

}