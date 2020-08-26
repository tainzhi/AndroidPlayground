package com.tainzhi.sample.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R

/**
 * Created by muqing on 2019/8/28.
 * Email: qfq61@qq.com
 */
class CenterAdapter(private val mList: List<Int>?, private val mRecyclerViewWidth: Int) :
    RecyclerView.Adapter<CenterAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_basic_horizontal_view, parent, false
            )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val p = holder.clRoot.layoutParams as MarginLayoutParams
        val margin = (mRecyclerViewWidth - p.width) / 2
        if (position == 0) {
            p.leftMargin = margin
            p.rightMargin = 0
        } else if (position == mList!!.size - 1) {
            p.leftMargin = 0
            p.rightMargin = margin
        } else {
            p.leftMargin = 0
            p.rightMargin = 0
        }
        holder.tvId.text = mList!![position].toString()
        holder.clRoot.layoutParams = p
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var clRoot: ConstraintLayout
        var tvId: TextView

        init {
            clRoot = view.findViewById(R.id.cl_root)
            tvId = view.findViewById(R.id.tv_basic_id)
        }
    }
}