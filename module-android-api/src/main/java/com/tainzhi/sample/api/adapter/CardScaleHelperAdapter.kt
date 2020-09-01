package com.tainzhi.sample.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import com.tainzhi.sample.util.ColorUtils

/**
 * Created by muqing on 2019-08-14.
 * Email: qfq61@qq.com
 */
class CardScaleHelperAdapter( private val mDataList: List<Int>) :
    RecyclerView.Adapter<CardScaleHelperAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate( R.layout.item_scale_card, parent, false )
        return MyViewHolder(view)
    }

    private var mOnItemClickListener: OnItemClickListener? = null
    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvId.text = mDataList[position].toString()
        holder.imageView.setBackgroundColor(
            ColorUtils.randomColor()
        )
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { view ->
                mOnItemClickListener?.onClick(
                    view,
                    position
                )
            }
            holder.itemView.setOnLongClickListener { view ->
                mOnItemClickListener?.onItemLongClick(view, position)
                false
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvId: TextView
        var imageView: ImageView

        init {
            tvId = view.findViewById(R.id.tv_basic_id)
            imageView = view.findViewById(R.id.imageView)
        }
    }

}

