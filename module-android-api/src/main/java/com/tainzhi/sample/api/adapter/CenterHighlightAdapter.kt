package com.tainzhi.sample.api.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R

/**
 * Created by muqing on 8/31/2019.
 * Email: qfq61@qq.com
 */
class CenterHighlightAdapter(private val mContext: Context, private val mList: List<Int>) :
    RecyclerView.Adapter<CenterHighlightAdapter.MyViewHolder>() {
    companion object {
        const val TAG = "CenterHighlightAdapter"
        const val CENTER = 0
        const val NOT_CENTER = 1
    }
    private var mCenterIndex = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_basic_horizontal_view, parent, false
            )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvId.text = mList[position].toString()
        Log.d(TAG, "position=${position}, ${holder.itemView.scaleX}")
        // FIXME: 2020/9/1 此处不知道为啥scaleX和scaleY不是1.0f
        // holder.itemView.scaleX = 1.0F
        // holder.itemView.scaleY = 1.0F
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isNotEmpty()) {
            val center = payloads[0] as Int
            if (center == CENTER) {
                holder.imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red))
            } else {
                holder.imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey))
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCenterIndex(newCenterIndex: Int) {
        // 原来的中心item改变, 不再是中心
        // payload不为null, 不是全更新
        notifyItemChanged(mCenterIndex, NOT_CENTER)
        notifyItemChanged(newCenterIndex, CENTER)
        mCenterIndex = newCenterIndex
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tv_basic_id)
        val imageView: ImageView = view.findViewById(R.id.imageView)

        fun setColor(color: Int) {
            imageView.setBackgroundColor(color)
        }
    }
}