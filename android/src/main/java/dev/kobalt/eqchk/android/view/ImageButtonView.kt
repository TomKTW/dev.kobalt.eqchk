package dev.kobalt.eqchk.android.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import dev.kobalt.eqchk.android.base.BaseContext

open class ImageButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageButton(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    init {
        onInit(attrs, defStyleAttr)
    }

    private var _backgroundTint: Int = Color.BLACK
    private var _rippleTint: Int = Color.BLACK
    private var _rippleInset: Int = 0

    var image: Drawable?
        get() = drawable
        set(value) {
            setImageDrawable(value)
        }

    var backgroundTint: Int
        get() = _backgroundTint
        set(value) {
            _backgroundTint = value
            updateBackground()
        }

    var imageTint: Int?
        get() = imageTintList?.defaultColor
        set(value) {
            imageTintList = value?.let { ColorStateList.valueOf(value) }
        }

    var rippleTint: Int
        get() = _rippleTint
        set(value) {
            _rippleTint = value
            updateBackground()
        }

    var rippleInset: Int
        get() = _rippleInset
        set(value) {
            _rippleInset = value
            updateBackground()
        }

    private fun onInit(attrs: AttributeSet?, defStyleAttr: Int) {
        rippleTint = Color.BLACK
        rippleInset = dp(4)
    }

    private fun updateBackground() {
        background = (LayerDrawable(
            arrayOf(
                ShapeDrawable(OvalShape()).apply { paint.color = backgroundTint },
                RippleDrawable(
                    ColorStateList.valueOf(rippleTint), null, ShapeDrawable(OvalShape())
                ).apply {
                    rippleInset.let { setLayerInset(0, it, it, it, it) }
                }
            )
        ))
    }

}