package com.tainzhi.sample.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import java.util.*

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-13 19:42
 * @description:
 */
class FilterableAdapter(
    private val mContext: Context,
    list: MutableList<String>
) : RecyclerView.Adapter<FilterableAdapter.MyViewHolder>(),
    Filterable {
    private var mList: List<String> = ArrayList()
    private var mOriginalList: MutableList<String> =
        ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_basic_vertial_view, parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.textView.text = mList[position]
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun removeItem(position: Int) {
        mList.removeAt(position)
        mOriginalList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: String, position: Int) {
        mList.add(position, item)
        mOriginalList.add(position, item)
        notifyItemInserted(position)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val constraintString = constraint.toString()
                var filteredList: MutableList<String> =
                    ArrayList()
                if (constraintString.isEmpty()) {
                    filteredList = mOriginalList
                } else {
                    for (s in mOriginalList) {
                        if (s.toLowerCase().contains(constraintString)) {
                            filteredList.add(s)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
            ) {
                mList = results.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView
        var foreground: View
        var background: View

        init {
            textView = view.findViewById(R.id.text_view)
            foreground = view.findViewById(R.id.cl_foreground)
            background = view.findViewById(R.id.cl_background)
        }
    }

    init {
        mList = list
        mOriginalList = list
    }
}