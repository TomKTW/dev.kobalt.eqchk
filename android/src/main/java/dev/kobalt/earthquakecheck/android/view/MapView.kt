package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.util.AttributeSet
import androidx.preference.PreferenceManager
import dev.kobalt.earthquakecheck.android.base.BaseContext
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView

open class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MapView(context, attrs), BaseContext {

    override fun requestContext(): Context = context.applicationContext

    companion object {
        fun initialize(context: Context) = Configuration.getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))
    }

}