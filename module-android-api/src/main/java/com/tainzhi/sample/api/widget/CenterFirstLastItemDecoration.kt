package com.tainzhi.sample.api.widget

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/8/31 13:33
 * @description: 怎么把首位的item居中了, 两种方法
 * 方法1: 通过设置首位的item的decoration
 * 方法2: 继承LayoutManager, 通过修改paddingLeft和paddingRight修改, 参考 [CustomLayoutManager]
 **/

// 方法2: 继承LayoutManager, 通过修改paddingLeft和paddingRight修改, 参考 [recyclerview.CustomLayoutManager]
class CenterFirstLastItemDecoration(private val itemOffset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 首尾item居中后, 所需要的padding
        var parentOffset = (parent.width - view.layoutParams.width )/ 2
        Log.d(TAG, "parentOffset=${parentOffset}")
        if (parentOffset < 0)  {
            parentOffset = (parent.measuredWidth - view.layoutParams.width) /2
        }
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = parentOffset
            outRect.right = itemOffset
        } else if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.right = parentOffset
        } else {
            outRect.right = itemOffset
        }

    }

    companion object {
        const val TAG = "CenterFirstLastItemDecoration"
    }
}