package dev.kobalt.earthquakecheck.android.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.isVisible
import dev.kobalt.earthquakecheck.android.base.BaseView
import dev.kobalt.earthquakecheck.android.component.LocationPoint
import dev.kobalt.earthquakecheck.android.databinding.ViewEventCardBinding
import dev.kobalt.earthquakecheck.android.event.EventEntity
import dev.kobalt.earthquakecheck.android.extension.toRelativeTimeString
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneId

class EventCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseView<ViewEventCardBinding>(context, attrs, defStyleAttr) {

    fun apply(item: EventEntity?, point: LocationPoint?) {
        viewBinding.apply {
            magnitudeLabel.text =
                item?.magnitude?.setScale(1, RoundingMode.HALF_EVEN)?.toString() ?: "-.-"
            timeLabel.text =
                item?.timestamp?.toRelativeTimeString(LocalDateTime.now(ZoneId.of("UTC")))
            val distance =
                if (item?.latitude != null && item.longitude != null && item.depth != null) {
                    point?.distanceFrom(
                        LocationPoint(item.latitude, item.longitude, -item.depth)
                    )?.toBigDecimal()?.divide(
                        BigDecimal(1000)
                    )?.setScale(0, RoundingMode.HALF_EVEN)
                } else null
            locationLabel.text = item?.location ?: "-"
            distanceImage.isVisible = distance != null
            distanceLabel.isVisible = distance != null
            distanceLabel.text = when {
                distance == null -> {
                    "-"
                }
                distance < BigDecimal.ONE -> {
                    "<1 km"
                }
                else -> distance.setScale(0, RoundingMode.HALF_EVEN)?.let { "$it km" }
            }
        }
    }

}