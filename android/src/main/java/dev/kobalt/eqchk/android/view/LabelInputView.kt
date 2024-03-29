package dev.kobalt.eqchk.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import dev.kobalt.eqchk.android.base.BaseContext

open class LabelInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    init {
        isFocusable = true
        isFocusableInTouchMode = true
    }

    /*
     * Possible workaround method overrides for crash that seems to occur on Samsung devices on long tap.
     *
     * References:
     * https://stackoverflow.com/questions/42926522/java-lang-nullpointerexception-with-nougat
     * https://stackoverflow.com/questions/52497289/app-crashes-when-long-clicking-on-text-view-hint
     *
     * Cause:
     * "Attempt to invoke virtual method 'boolean android.widget.Editor$SelectionModifierCursorController.isDragAcceleratorActive()' on a null object reference"
     */

    override fun performLongClick(): Boolean {
        return try {
            super.performLongClick()
        } catch (e: NullPointerException) {
            e.printStackTrace()
            true
        }
    }

    override fun performLongClick(x: Float, y: Float): Boolean {
        return try {
            super.performLongClick(x, y)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return try {
            super.onTouchEvent(event)
        } catch (e: NullPointerException) {
            e.printStackTrace(); true
        }
    }

    override fun performClick(): Boolean {
        return try {
            super.performClick()
        } catch (e: NullPointerException) {
            e.printStackTrace()
            true
        }
    }

}

