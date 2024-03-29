package dev.kobalt.eqchk.android.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.SwitchCompat
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.base.BaseContext

open class OptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : StackView(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    val titleLabel = LabelView(context).apply {
        setTextColor(getResourceColor(R.color.black))
        fontSize = sp(16)
    }

    val subtitleLabel = LabelView(context).apply {
        setTextColor(getResourceColor(R.color.black))
        alpha = 0.75f
        fontSize = sp(14)
    }

    val optionSwitch = SwitchCompat(context).apply {
        isClickable = false
    }

    init {
        onInit(context, attrs, defStyleAttr)
    }

    private fun onInit(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        orientation = HORIZONTAL
        val labelStack = StackView(context).apply {
            orientation = VERTICAL
            addView(titleLabel)
            addView(subtitleLabel)
            gravity = Gravity.CENTER
        }
        addView(labelStack, LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f))
        addView(optionSwitch)
        background = RippleDrawable(
            ColorStateList.valueOf(getResourceColor(R.color.gray)),
            null,
            ShapeDrawable(RectShape())
        )
    }

}