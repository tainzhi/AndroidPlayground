package com.tainzhi.sample.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import com.tainzhi.sample.util.ColorUtils
import com.tainzhi.sample.util.dpToPx

/**
 * Created by muqing on 2019-08-14.
 * Email: qfq61@qq.com
 */
class CardScaleHelperAdapter(context: Context, private val mDataList: List<Int>) :
    RecyclerView.Adapter<CardScaleHelperAdapter.MyViewHolder>() {
    
    private val cardAdapterHelper = CardAdapterHelper(context)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate( R.layout.item_scale_card, parent, false )
        cardAdapterHelper.onCreateViewHolder(parent, view)
        return MyViewHolder(view)
    }

    private var mOnItemClickListener: OnItemClickListener? = null
    override fun getItemCount(): Int {
        return mDataList!!.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvId: TextView
        var imageView: ImageView

        init {
            tvId = view.findViewById(R.id.tv_basic_id)
            imageView = view.findViewById(R.id.imageView)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        cardAdapterHelper.onBindViewHolder(holder.itemView, position, mDataList.size)
        
        holder.tvId.text = mDataList!![position].toString()
        holder.imageView.setBackgroundColor(
            ColorUtils.randomColor()
        )
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { view ->
                mOnItemClickListener!!.onClick(
                    view,
                    position
                )
            }
            holder.itemView.setOnLongClickListener { view ->
                mOnItemClickListener!!.onItemLongClick(view, position)
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
}

class CardAdapterHelper(context: Context) {
    val padding = context.dpToPx<Int>(15)
    val margin = context.dpToPx<Int>(15)
    fun onCreateViewHolder(parent: ViewGroup, itemView: View) {
        val lp = itemView.layoutParams as RecyclerView.LayoutParams
        lp.width = parent.width - parent.context.dpToPx<Int>(2 * (padding + margin))
        itemView.layoutParams = lp
    }
    
    fun onBindViewHolder(itemView: View, position: Int, itemCount: Int) {
        // itemView.setPadding(padding, 0, padding, 0)
        val leftMargin = if (position == 0) 0 else margin
        // val rightMargin = if (position == itemCount - 1) padding + margin else 0
        val rightMargin = 0
        setViewMargin(itemView, leftMargin, 0, rightMargin, 0)
    }
    
    private fun setViewMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val lp = view.layoutParams as (ViewGroup.MarginLayoutParams)
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            view.layoutParams = lp
        }
    }
}

