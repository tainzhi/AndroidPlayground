package com.tainzhi.sample.customview

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.android.common.base.ui.BaseViewBindingActivity
import com.tainzhi.sample.customview.databinding.ActivityCustomLayoutMangerBinding
import kotlin.math.max

class RecyclerViewCustomLayoutMangerActivity : BaseViewBindingActivity<ActivityCustomLayoutMangerBinding>() {
    override fun initView() {
        mBinding.recyclerV.run {
            layoutManager = CustomLayoutManger(context)
            adapter = ItemDecorationAdapter(generateFakeData())
             addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                 setDrawable(getDrawable(R.drawable.item_divider)!!)
             })
        }
    }

    private fun generateFakeData(): List<String> {
        return List(30) { index -> "Item $index"}
    }
}

class CustomLayoutManger(context: Context): RecyclerView.LayoutManager() {
    // 总共的滑动距离
    private var sumDy = 0
    // item的高度和
    private var totalHeight = 0
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        var offsetY = 0
        for (i in 0 until itemCount) {
            val view = recycler?.getViewForPosition(i) ?: return
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            layoutDecorated(view, 0, offsetY, width, offsetY + height)
            offsetY += height
        }
        totalHeight = max(offsetY, getVerticalSpace())
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        // dy 表示手指在屏幕上的滑动位移
        // -dy <0 向上滑动
        // dy >0  向下滑动
        var travel = dy
        // 已经到顶, 不能再移动
        if (sumDy + dy < 0) {
            travel = -sumDy
        } else if (sumDy + dy > totalHeight - getVerticalSpace()) {
            // mSumDy + dy 表示当前的移动距离，
            // mTotalHeight - getVerticalSpace()表示当滑动到底时滚动的总距离；
            // 已经到底, 不能在滑动
            travel = totalHeight - getVerticalSpace() - sumDy
        }
        sumDy += travel
        // 平滑容器内的item
        offsetChildrenVertical(-travel)
        return dy
    }

    private fun getVerticalSpace(): Int {
        return height - paddingBottom - paddingTop
    }
}

