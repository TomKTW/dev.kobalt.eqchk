package dev.kobalt.earthquakecheck.android.home

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ListPopupWindow
import dev.kobalt.earthquakecheck.android.R
import dev.kobalt.earthquakecheck.android.base.BaseStringListAdapter

/* WARNING: If BaseContext is implemented in this class, it will crash on debug! */
class HomeFilterPopupWindow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.listPopupWindowStyle,
    defStyleRes: Int = 0
) : ListPopupWindow(context, attrs, defStyleAttr, defStyleRes) {

    var onItemClick: ((position: Int) -> Unit)? = null
    var onDismiss: (() -> Unit)? = null

    init {
        width = androidx.appcompat.widget.ListPopupWindow.MATCH_PARENT
        setDropDownGravity(Gravity.BOTTOM)
        setAdapter(
            BaseStringListAdapter(
                listOf(
                    context.getString(R.string.home_map_filter_none),
                    context.getString(R.string.home_map_filter_depth),
                    context.getString(R.string.home_map_filter_timestamp),
                    context.getString(R.string.home_map_filter_estimated_intensity),
                    context.getString(R.string.home_map_filter_community_intensity)
                )
            )
        )
        setOnItemClickListener { _, _, position, _ ->
            onItemClick?.invoke(position)
            dismiss()
        }
        setOnDismissListener {
            onDismiss?.invoke()
        }
    }

}
