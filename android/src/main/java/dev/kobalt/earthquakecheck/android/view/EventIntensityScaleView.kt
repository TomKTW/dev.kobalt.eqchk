package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.core.content.ContextCompat
import dev.kobalt.earthquakecheck.android.R
import dev.kobalt.earthquakecheck.android.base.BaseContext
import dev.kobalt.earthquakecheck.android.event.EventIntensity
import kotlin.properties.Delegates

open class EventIntensityScaleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : StackView(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    var value: EventIntensity? by Delegates.observable(null) { _, _, newValue ->
        labels.forEach { it.alpha = if (it.tag == newValue?.number) 1.0f else 0.15f }
    }

    val labels = EventIntensity.values().map {
        LabelView(context, attrs, defStyleAttr).apply {
            text = it.number
            tag = it.number
            gravity = Gravity.CENTER
            alpha = if (tag == value?.number) 1.0f else 0.15f
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            setBackgroundColor(ContextCompat.getColor(context, it.color))
        }
    }

    init {
        orientation = HORIZONTAL
        onInit(context, attrs, defStyleAttr)
    }

    private fun onInit(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        labels.forEach { addView(it, LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f)) }
    }

}

