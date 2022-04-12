package dev.kobalt.earthquakecheck.android.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

open class BaseStringListAdapter(
    private val data: List<String>
) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): String {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, null)
        val holder = view?.tag as? ViewHolder
            ?: ViewHolder(view.findViewById(android.R.id.text1))
        view.tag = holder
        holder.label.text = getItem(position)
        return view
    }

    class ViewHolder(val label: TextView)

}