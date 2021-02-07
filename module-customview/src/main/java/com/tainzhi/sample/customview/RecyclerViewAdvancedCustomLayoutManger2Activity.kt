package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.Rect
import android.util.SparseArray
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.android.common.base.ui.BaseViewBindingActivity
import com.tainzhi.sample.customview.databinding.ActivityCustomLayoutMangerBinding
import kotlin.math.max

class RecyclerViewAdvancedCustomLayoutManger2Activity : BaseViewBindingActivity<ActivityCustomLayoutMangerBinding>() {
    override fun initView() {
        mBinding.recyclerV.run {
            layoutManager = AdvancedCustomLayoutManger2(context)
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

// 参考: https://blog.csdn.net/harvic880925/article/details/84979161
class AdvancedCustomLayoutManger2(context: Context): RecyclerView.LayoutManager() {
    // 总共的滑动距离
    private var sumDy = 0
    // item的高度和
    private var totalHeight = 0
    private var itemWidth = 0
    private var itemHeight = 0
    // 记录每个item的位置
    private val itemRects = SparseArray<Rect>()
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        // adapter中没有数据, 将所有的item从屏幕上剥离, 清空当前屏幕
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
        // 将所有的item view从RecyclerView剥离, 然后再重新添加addView
        detachAndScrapAttachedViews(recycler)

        // 申请一个 HolderView
        val childView = recycler.getViewForPosition(0)
        // 测量HolderView后, 方便得出 width & height
        measureChildWithMargins(childView, 0, 0)
        itemWidth = getDecoratedMeasuredWidth(childView)
        itemHeight = getDecoratedMeasuredHeight(childView)

        var offsetY = 0
        // item大小固定, 为了布局方便, 记录下来其位置
        for (i in 0 until itemCount) {
            itemRects.put(i, Rect(0, offsetY, itemWidth, offsetY + itemHeight))
            offsetY += itemHeight
        }

        // 可见item 数目
        val visibleCount = getVerticalSpace() / itemHeight
        for (i in 0..visibleCount) {
            val rect = itemRects[i]
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom)
        }
        // offsetY是所有item的总高度，而当item填不满RecyclerView时，
        // offsetY应该是比RecyclerView的真正高度小的，而此时的真正的高度应该是RecyclerView本身所设置的高度。
        totalHeight = max(offsetY, getVerticalSpace())
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State?
    ): Int {
        if (childCount == 0) return dy
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

        // travel >= 0 向上滚动
        // travel < 0  向下滚动
        // 回收越界的子view
        for (i in (childCount - 1)..0) {
            val child = getChildAt(i) ?: continue
            val pos = getPosition(child)
            val rect = itemRects[pos]

            if (travel > 0) {
                // 当前item上移动 getDecoratedMeasuredHeight(child) - travel后, 超出上边界
                if (getDecoratedBottom(child) - travel < 0) {
                    if (!Rect.intersects(rect, getVisibleArea())) {
                        removeAndRecycleView(child, recycler)
                        continue
                    } else {
                        // 屏幕的item直接变化, 不需要remove
                        layoutDecoratedWithMargins(child, rect.left, rect.top - sumDy, rect.right, rect.bottom - sumDy)
                        child.rotationY = child.rotationY + 1
                    }
                }
            } else if (travel < 0) {
                // 下移动超出下边界
                if  (getDecoratedBottom(child) - travel > height - paddingBottom){
                    if (!Rect.intersects(rect, getVisibleArea())) {
                        removeAndRecycleView(child, recycler)
                        continue
                    } else {
                        // 屏幕的item直接变化, 不需要remove
                        layoutDecoratedWithMargins(child, rect.left, rect.top - sumDy, rect.right, rect.bottom - sumDy)
                        child.rotationY = child.rotationY + 1
                    }
                }
            }
        }

        val lastView = getChildAt(childCount - 1) ?: return dy
        val firstView = getChildAt(0) ?: return dy
        // 先把所有的view detach
        detachAndScrapAttachedViews(recycler)
        sumDy += travel
        if (travel >= 0) {
            val minPos = getPosition(firstView)
            for (i in minPos until itemCount) {
                insertView(i, getVisibleArea(), recycler, false)
            }
        } else {
            val maxPos = getPosition(lastView)
            for (i in maxPos downTo 0) {
                insertView(i, getVisibleArea(), recycler, true)
            }
        }

        return travel
    }

    // 新增travel移动后, 当前屏幕所在的位置
    // sumDy 上次移动距离
    private fun getVisibleArea(): Rect {
        return Rect(paddingLeft, paddingTop + sumDy ,
            width + paddingRight, getVerticalSpace() + sumDy )
    }

    // 当前RecyclerView可见垂直高度
    private fun getVerticalSpace(): Int {
        return height - paddingBottom - paddingTop
    }

    private fun insertView(pos: Int, visibleRect: Rect, recycler: RecyclerView.Recycler, isFirstPos: Boolean) {
        val rect = itemRects[pos]
        if (Rect.intersects(visibleRect, rect)) {
            val child = recycler.getViewForPosition(pos)
            if (isFirstPos) {
                addView(child, 0)
            } else {
                addView(child)
            }
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, rect.left, rect.top - sumDy, rect.right, rect.bottom - sumDy)

            child.rotationY = child.rotationY + 1
        }
    }
}

