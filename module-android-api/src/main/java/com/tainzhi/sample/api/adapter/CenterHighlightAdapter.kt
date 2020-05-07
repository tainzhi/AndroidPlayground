package com.tainzhi.sample.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R

/**
 * Created by muqing on 8/31/2019.
 * Email: qfq61@qq.com
 */
class CenterHighlightAdapter(
    private val mContext: Context,
    private val mList: List<Int>
) : RecyclerView.Adapter<CenterHighlightAdapter.MyViewHolder>() {
    private var mCenterIndex = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_basic_horizontal_view, parent, false
            )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.tvId.text = mList[position].toString()
        if (position == mCenterIndex) {
            holder.clRoot.setBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.red
                )
            )
        } else {
            holder.clRoot.setBackgroundColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.grey
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCenterIndex(newCenterIndex: Int) {
        notifyItemChanged(mCenterIndex)
        notifyItemChanged(newCenterIndex)
        mCenterIndex = newCenterIndex
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