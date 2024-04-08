package dev.kobalt.eqchk.android.base

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

interface BaseContext {

    fun requestContext(): Context

    private fun getDimensionValue(type: Int, value: Float): Float {
        return TypedValue.applyDimension(type, value, requestContext().resources.displayMetrics)
    }

    fun dp(value: Int): Int {
        return getDimensionValue(TypedValue.COMPLEX_UNIT_DIP, value.toFloat()).toInt()
    }

    fun sp(value: Int): Int {
        return getDimensionValue(TypedValue.COMPLEX_UNIT_SP, value.toFloat()).toInt()
    }

    fun getResourceColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(requestContext(), id)
    }

    fun getResourceColorState(@ColorRes id: Int): ColorStateList {
        return ColorStateList.valueOf(getResourceColor(id))
    }

    fun getResourceDrawable(@DrawableRes id: Int): Drawable {
        return AppCompatResources.getDrawable(requestContext(), id)!!
    }

    fun getResourceString(@StringRes id: Int, vararg values: Any): String {
        return if (values.isEmpty()) {
            requestContext().getString(id)
        } else {
            requestContext().getString(id, *values)
        }
    }

    fun getResourceStringPlural(
        @PluralsRes id: Int, quantity: Int,
        vararg values: Any
    ): String {
        return if (values.isEmpty()) {
            requestContext().resources.getQuantityString(id, quantity)
        } else {
            requestContext().resources.getQuantityString(id, quantity, *values)
        }
    }

    fun getResourceFont(
        @FontRes id: Int
    ): Typeface {
        return ResourcesCompat.getFont(requestContext(), id)!!
    }

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requestContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun arePermissionsGranted(vararg permissions: String): Boolean {
        return permissions.all(this::isPermissionGranted)
    }

    fun obtainStyledAttributes(
        set: AttributeSet? = null,
        attrs: IntArray,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0,
        function: (TypedArray) -> (Unit)
    ) {
        requestContext().theme.obtainStyledAttributes(
            set,
            attrs,
            defStyleAttr,
            defStyleRes
        ).let {
            try {
                function.invoke(it)
            } finally {
                it.recycle()
            }
        }
    }

    fun showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requestContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun TypedArray.getValueString(@StyleableRes index: Int): String? {
        return takeIf { it.hasValue(index) }?.getString(index)
    }

    fun TypedArray.getValueColor(@StyleableRes index: Int): Int? {
        return takeIf { it.hasValue(index) }?.getColor(index, Color.BLACK)
    }

    fun TypedArray.getValueDrawable(@StyleableRes index: Int): Drawable? {
        return takeIf { it.hasValue(index) }?.getDrawable(index)
    }

    fun TypedArray.getValuePixel(@StyleableRes index: Int): Int? {
        return takeIf { it.hasValue(index) }?.getDimensionPixelSize(index, 0)
    }

    fun TypedArray.getValueFont(@StyleableRes index: Int): Typeface? {
        return takeIf { it.hasValue(index) }?.getResourceId(index, -1)?.takeIf { it != -1 }
            ?.let { getResourceFont(it) }
    }

}
