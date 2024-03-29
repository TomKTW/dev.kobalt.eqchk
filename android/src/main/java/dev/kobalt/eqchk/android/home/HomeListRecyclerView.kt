package dev.kobalt.eqchk.android.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import dev.kobalt.eqchk.android.R
import dev.kobalt.eqchk.android.component.LocationPoint
import dev.kobalt.eqchk.android.databinding.ViewEventCardBinding
import dev.kobalt.eqchk.android.event.EventEntity
import dev.kobalt.eqchk.android.extension.toRelativeTimeString
import dev.kobalt.eqchk.android.view.RecyclerView
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.properties.Delegates

class HomeListRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun getAdapter(): Adapter = super.getAdapter() as Adapter

    override fun getLayoutManager(): LinearLayoutManager =
        super.getLayoutManager() as LinearLayoutManager

    init {
        adapter = Adapter()
        layoutManager = LinearLayoutManager(context)
    }

    var onItemSelect: ((EventEntity) -> Unit)?
        get() {
            return adapter.onItemSelect
        }
        set(value) {
            adapter.onItemSelect = value
        }

    @SuppressLint("NotifyDataSetChanged")
    class Adapter(
        var onItemSelect: ((EventEntity) -> Unit)? = null
    ) : RecyclerView.Adapter<Holder<*>>() {

        private val differ: AsyncListDiffer<EventEntity> =
            AsyncListDiffer(this, object : DiffUtil.ItemCallback<EventEntity>() {
                override fun areItemsTheSame(
                    oldItem: EventEntity,
                    newItem: EventEntity
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: EventEntity,
                    newItem: EventEntity
                ): Boolean = oldItem == newItem
            })

        var point: LocationPoint? by Delegates.observable(null) { _, _, _ ->
            notifyDataSetChanged()
        }

        var list: List<EventEntity>
            get() = differ.currentList
            set(value) = differ.submitList(value)

        override fun getItemViewType(position: Int): Int {
            return when (list.getOrNull(position)) {
                is EventEntity -> 0
                else -> throw Exception("Invalid item type.")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<*> {
            when (viewType) {
                0 -> return ItemHolder(
                    ViewEventCardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ).apply {
                    binding.root.setOnClickListener {
                        list.getOrNull(adapterPosition)?.let {
                            onItemSelect?.invoke(it)
                        }
                    }
                }
                else -> throw Exception("Invalid view type.")
            }
        }

        override fun onBindViewHolder(holder: Holder<*>, position: Int) {
            when (holder) {
                is ItemHolder -> {
                    list.getOrNull(holder.adapterPosition).let { item ->
                        holder.binding.apply {
                            magnitudeLabel.text =
                                item?.magnitude?.setScale(1, RoundingMode.HALF_EVEN).toString()
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
                                    "<1 ${root.getResourceString(R.string.kilometers)}"
                                }
                                else -> distance.setScale(0, RoundingMode.HALF_EVEN)
                                    ?.let { "$it ${root.getResourceString(R.string.kilometers)}" }
                            }
                        }
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    open class Holder<T : ViewBinding>(binding: T) : RecyclerView.BindingHolder<T>(
        binding
    )

    class ItemHolder(binding: ViewEventCardBinding) : Holder<ViewEventCardBinding>(binding)

}