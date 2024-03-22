package dev.kobalt.earthquakecheck.android.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.view.isVisible

class SlideAnimation(
    private val view: View,
    duration: Int,
    private val offset: Int,
    private val invert: Boolean
) :
    Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        view.isVisible = true
        if (invert) {
            if (view.translationY == 0f) return
            view.translationY = (offset * (1.0f - interpolatedTime))
        } else {
            if (view.translationY == offset.toFloat()) return
            view.translationY = (offset * interpolatedTime)
        }
    }


    init {
        setDuration(duration.toLong())
    }
}