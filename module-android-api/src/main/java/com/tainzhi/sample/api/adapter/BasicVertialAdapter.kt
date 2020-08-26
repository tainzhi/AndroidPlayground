package com.tainzhi.sample.api.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.sample.api.R

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-09 08:50
 * @description:
 */
class BasicVertialAdapter(isWaterFall: Boolean) :
    BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_basic_vertial_view) {
    var isWaterFall = false
    override fun convert(helper: BaseViewHolder, item: String?) {
        // 瀑布流式布局
        if (isWaterFall) {
            val view = helper.itemView
            val params = view.layoutParams
            val newHeight = getRandomHeight(params.height.toDouble())
            params.height = newHeight
            view.layoutParams = params
            helper.setText(R.id.text_view, item)
        } else {
            helper.setText(R.id.text_view, item)
        }
    }

    private fun getRandomHeight(oriheight: Double): Int {
        var height = oriheight + oriheight * Math.random()
        if (height > 1300) {
            height = 300.0
        }
        return height.toInt()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    // 模 9 为 0 的 item, 占位为整个屏幕 2/2
                    // 模 9 为非 1 的 item, 占位为整个屏幕的 1/2
                    return if (position % 9 == 0) 2 else 1
                }
            }
        }
    }

    init {
        this.isWaterFall = isWaterFall
    }
}