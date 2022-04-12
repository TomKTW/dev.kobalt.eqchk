package dev.kobalt.earthquakecheck.android.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import dev.kobalt.earthquakecheck.android.R
import dev.kobalt.earthquakecheck.android.component.LocationPoint
import dev.kobalt.earthquakecheck.android.event.EventEntity
import dev.kobalt.earthquakecheck.android.extension.toEventIntensity
import dev.kobalt.earthquakecheck.android.view.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.OverlayItem
import java.time.LocalDateTime
import kotlin.properties.Delegates

class HomeMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MapView(context, attrs) {

    var onTap: (() -> (Unit))? = null
    var onItemTap: ((Item) -> (Boolean))? = null
    var onItemLongPress: ((Item) -> (Boolean))? = null

    var items = emptyList<Item>()

    private val color1 =
        Color.argb(255, (255 * 1.0).toInt(), (255 * 1.0).toInt(), (255 * 1.0).toInt())
    private val color2 =
        Color.argb(255, (255 * 0.9).toInt(), (255 * 0.9).toInt(), (255 * 0.9).toInt())
    private val color3 =
        Color.argb(255, (255 * 0.8).toInt(), (255 * 0.8).toInt(), (255 * 0.8).toInt())
    private val color4 =
        Color.argb(255, (255 * 0.7).toInt(), (255 * 0.7).toInt(), (255 * 0.7).toInt())
    private val color5 =
        Color.argb(255, (255 * 0.6).toInt(), (255 * 0.6).toInt(), (255 * 0.6).toInt())
    private val color6 =
        Color.argb(255, (255 * 0.5).toInt(), (255 * 0.5).toInt(), (255 * 0.5).toInt())
    private val color7 =
        Color.argb(255, (255 * 0.4).toInt(), (255 * 0.4).toInt(), (255 * 0.4).toInt())
    private val color8 =
        Color.argb(255, (255 * 0.3).toInt(), (255 * 0.3).toInt(), (255 * 0.3).toInt())
    private val color9 =
        Color.argb(255, (255 * 0.2).toInt(), (255 * 0.2).toInt(), (255 * 0.2).toInt())
    private val color10 =
        Color.argb(255, (255 * 0.1).toInt(), (255 * 0.1).toInt(), (255 * 0.1).toInt())

    var type: LegendType by Delegates.observable(LegendType.None) { _, _, newValue ->
        items.forEach {
            val drawable = (it.drawable as? GradientDrawable)
            when (newValue) {
                LegendType.None -> drawable?.setColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red
                    )
                )
                LegendType.Timestamp -> drawable?.setColor(
                    when {
                        it.data.timestamp == null -> Color.BLACK
                        it.data.timestamp > LocalDateTime.now().minusDays(1) -> color1
                        it.data.timestamp > LocalDateTime.now().minusDays(2) -> color2
                        it.data.timestamp > LocalDateTime.now().minusDays(3) -> color3
                        it.data.timestamp > LocalDateTime.now().minusDays(4) -> color4
                        it.data.timestamp > LocalDateTime.now().minusDays(5) -> color5
                        it.data.timestamp > LocalDateTime.now().minusDays(6) -> color6
                        it.data.timestamp > LocalDateTime.now().minusDays(7) -> color7
                        it.data.timestamp > LocalDateTime.now().minusDays(8) -> color8
                        it.data.timestamp > LocalDateTime.now().minusDays(9) -> color9
                        it.data.timestamp > LocalDateTime.now().minusDays(10) -> color10
                        else -> Color.BLACK
                    }
                )
                LegendType.Depth -> drawable?.setColor(
                    when {
                        it.data.depth == null -> Color.BLACK
                        it.data.depth < 10.0 -> color1
                        it.data.depth < 20.0 -> color2
                        it.data.depth < 30.0 -> color3
                        it.data.depth < 40.0 -> color4
                        it.data.depth < 50.0 -> color5
                        it.data.depth < 60.0 -> color6
                        it.data.depth < 70.0 -> color7
                        it.data.depth < 80.0 -> color8
                        it.data.depth < 90.0 -> color9
                        it.data.depth < 100.0 -> color10
                        else -> Color.BLACK
                    }
                )
                LegendType.EstimatedIntensity -> drawable?.setColor(
                    ContextCompat.getColor(
                        context,
                        it.data.estimatedIntensity?.toEventIntensity()?.color ?: R.color.black
                    )
                )
                LegendType.CommunityIntensity -> drawable?.setColor(
                    ContextCompat.getColor(
                        context,
                        it.data.communityIntensity?.toEventIntensity()?.color ?: R.color.black
                    )
                )
            }
        }
        invalidate()
    }

    fun updateItems(list: List<EventEntity>) {
        items = list.map { entity ->
            Item(entity).also { item ->
                val drawable = GradientDrawable().also {
                    it.setColor(Color.RED)
                    it.shape = GradientDrawable.OVAL
                    it.setStroke(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            1f,
                            resources.displayMetrics
                        ).toInt(), Color.BLACK
                    )
                    it.setSize(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            2f * (item.data.magnitude?.toFloat() ?: 0f),
                            resources.displayMetrics
                        ).toInt(), TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            2f * (item.data.magnitude?.toFloat() ?: 0f),
                            resources.displayMetrics
                        ).toInt()
                    )
                }
                item.markerHotspot = OverlayItem.HotspotPlace.CENTER
                item.setMarker(drawable)
            }
        }
        overlay.removeAllItems()
        overlay.addItems(items)
        invalidate()
    }

    private val overlay = ItemizedIconOverlay(
        mutableListOf(),
        object : ItemizedIconOverlay.OnItemGestureListener<Item> {
            override fun onItemSingleTapUp(
                index: Int,
                item: Item
            ): Boolean = onItemTap?.invoke(item) ?: false

            override fun onItemLongPress(
                index: Int,
                item: Item
            ): Boolean = onItemLongPress?.invoke(item) ?: false
        }, context
    )

    private var locationItem = OverlayItem(null, null, null)

    private val overlayLocation = ItemizedIconOverlay(
        mutableListOf(),
        object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
            override fun onItemSingleTapUp(
                index: Int,
                item: OverlayItem
            ): Boolean = false

            override fun onItemLongPress(
                index: Int,
                item: OverlayItem
            ): Boolean = false
        }, context
    )

    var location: LocationPoint? by Delegates.observable(null) { _, _, newValue ->
        overlayLocation.removeItem(locationItem)
        if (newValue != null) {
            locationItem =
                OverlayItem(null, null, GeoPoint(newValue.latitude, newValue.longitude))
            val drawable = GradientDrawable().also {
                it.setColor(Color.BLUE)
                it.shape = GradientDrawable.OVAL
                it.setStroke(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        2f,
                        resources.displayMetrics
                    ).toInt(), Color.WHITE
                )
                it.setSize(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8f,
                        resources.displayMetrics
                    ).toInt(), TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8f,
                        resources.displayMetrics
                    ).toInt()
                )
            }
            locationItem.markerHotspot = OverlayItem.HotspotPlace.CENTER
            locationItem.setMarker(drawable)
            overlayLocation.addItem(locationItem)
        }
        invalidate()
    }

    private val overlayTouch = object : Overlay() {
        override fun onSingleTapConfirmed(
            e: MotionEvent?,
            mapView: org.osmdroid.views.MapView?
        ): Boolean {
            onTap?.invoke()
            return super.onSingleTapConfirmed(e, mapView)
        }
    }

    init {
        minZoomLevel = 1.5
        isTilesScaledToDpi = true
        setMultiTouchControls(true)
        zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        setTileSource(TileSourceFactory.MAPNIK)
        overlays.add(overlayTouch)
        overlays.add(overlay)
        overlays.add(overlayLocation)
    }

    class Item(val data: EventEntity) :
        OverlayItem("", "", GeoPoint(data.latitude!!, data.longitude!!))

    enum class LegendType {
        None, Timestamp, Depth, EstimatedIntensity, CommunityIntensity
    }

}