package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.kobalt.earthquakecheck.android.base.BaseContext

open class RefreshView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs), BaseContext {

    override fun requestContext(): Context = context.applicationContext

}