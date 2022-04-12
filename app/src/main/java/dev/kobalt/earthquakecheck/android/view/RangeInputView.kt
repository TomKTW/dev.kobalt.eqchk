package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import dev.kobalt.earthquakecheck.android.base.BaseContext

open class RangeInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RangeSlider(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    init {
        labelBehavior = LabelFormatter.LABEL_GONE
    }

}