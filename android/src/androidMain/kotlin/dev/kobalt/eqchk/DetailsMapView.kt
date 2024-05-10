package dev.kobalt.eqchk.android.details

/*
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import androidx.preference.PreferenceManager
import dev.kobalt.eqchk.android.component.LocationPoint
import dev.kobalt.eqchk.android.event.EventEntity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.OverlayItem
import kotlin.properties.Delegates

open class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : org.osmdroid.views.MapView(context, attrs) {

    companion object {
        fun initialize(context: Context) = Configuration.getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))
    }

}

class DetailsMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MapView(context, attrs) {

    var onTap: (() -> (Unit))? = null
    var onItemTap: ((Item) -> (Boolean))? = null
    var onItemLongPress: ((Item) -> (Boolean))? = null

    var items = emptyList<Item>()

    fun updateItem(event: EventEntity) {
        items = listOf(event).map {
            Item(it).also { item ->
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
                /*val drawable = ShapeDrawable(OvalShape()).also {
                    it.intrinsicHeight = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8f,
                        resources.displayMetrics
                    ).toInt()
                    it.intrinsicWidth = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        8f,
                        resources.displayMetrics
                    ).toInt()
                }*/
                //val drawable = ContextCompat.getDrawable(context, if (it.data.magnitude!! > BigDecimal(4)) R.drawable.ic_baseline_circle_24 else  R.drawable.ic_baseline_circle_16)?.mutate()
                item.setMarker(drawable)
                controller.setCenter(item.point)
                controller.setZoom(5.0)
            }
        }
        overlay.removeAllItems()
        overlay.addItems(items)
        invalidate()
    }

    val overlay = ItemizedIconOverlay(
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

    val overlayLocation = ItemizedIconOverlay(
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

    val overlayTouch = object : Overlay() {
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
    }

    class Item(val data: EventEntity) :
        OverlayItem("", "", GeoPoint(data.latitude!!, data.longitude!!))

}
*/