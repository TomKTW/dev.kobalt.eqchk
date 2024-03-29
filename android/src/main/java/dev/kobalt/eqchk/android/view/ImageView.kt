package dev.kobalt.eqchk.android.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import dev.kobalt.eqchk.android.base.BaseContext

open class ImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    var image: Drawable?
        get() = drawable
        set(value) {
            setImageDrawable(value)
        }

    var imageTint: Int?
        get() = imageTintList?.defaultColor
        set(value) {
            imageTintList = value?.let { ColorStateList.valueOf(it) }
        }


}

