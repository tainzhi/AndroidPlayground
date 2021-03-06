package com.tainzhi.sample.api.recyclerview

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.CenterHighlightAdapter
import com.tainzhi.sample.util.ColorUtils

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/8/26 17:24
 * @description:
 **/

/**
 * 使得首尾item能够居中, 而且还有缩放效果
 *
 * @itemOffset item之间的间距 单位px
 * @scale 是否需要缩放,缩放比率
 * @onCenter 传递中心item事件
 *
 * 因为添加了itemOffset, 使得整个RecyclerView的center移动了itemOffset / 2
 */
class ItemDecorationSnapHelper(private val itemOffset: Int, val scale: Float = 1.0f, onCenter: ((centerIndex: Int) -> Unit)? = null) : LinearSnapHelper() {
    companion object {
        const val TAG = "ItemDecorationSnapHelper"
    }
    private var mHorizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToCenter(
                layoutManager, targetView,
                getHorizontalHelper(layoutManager)
            )
        } else {
            out[0] = 0
        }

        // TODO: 2020/8/31  Vertical Center
        // if (layoutManager.canScrollVertically()) {
        //     out[1] = distanceToCenter(
        //         layoutManager, targetView,
        //         getVerticalHelper(layoutManager)
        //     )
        // } else {
        //     out[1] = 0
        // }
        return out
    }

    private fun distanceToCenter(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View, helper: OrientationHelper
    ): Int {
        // 有itemOffset之后, 每个新item等于旧item + 左边的itemOffset
        // 起始位置为 helper.getDecoratedStart(targetView) - itemOffset
        // 宽度为 helper.getDecoratedMeasurement(targetView) + itemOffset
        val childCenter = (helper.getDecoratedStart(targetView)
                - itemOffset
                + (helper.getDecoratedMeasurement(targetView) + itemOffset) / 2)
        val containerCenter = helper.startAfterPadding + helper.totalSpace / 2
        return childCenter - containerCenter
    }

    private fun getHorizontalHelper(
        layoutManager: RecyclerView.LayoutManager
    ): OrientationHelper {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper!!
    }

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        recyclerView?.addOnScrollListener(onScrollListener)

    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (scale == 1.0f) return

            val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val childCount = layoutManager.childCount
            val helper = getHorizontalHelper(layoutManager)
            val recyclerViewCenter = helper.totalSpace / 2
            for (i in 0 until childCount) {

                val itemView = layoutManager.getChildAt(i)!!
                val itemCenterX = itemView.layoutParams.width/2 + itemView.x
                //                   ★ 两边的图片缩放比例
                // val scale = 0.9f
                //                     ★某个item中心X坐标距recyclerview中心X坐标的偏移量
                val offX = Math.abs(itemCenterX - recyclerViewCenter)
                //                    ★ 在一个item的宽度范围内，item从1缩放至scale，那么改变了（1-scale），从下列公式算出随着offX变化，item的变化缩放百分比
                val percent = offX * (1 - scale) / (itemView.layoutParams).width
                //                   ★  取反哟
                var interpretateScale = 1 - percent
                //                    这个if不走的话，得到的是多级渐变模式
                if (interpretateScale < scale) {
                    interpretateScale = scale
                }
                Log.d(TAG, "itemCenterX=${itemCenterX}, position=${layoutManager.getPosition(itemView)}, scale=${interpretateScale}")
                itemView.scaleX = interpretateScale
                itemView.scaleY = interpretateScale
                CenterHighlightAdapter.MyViewHolder(itemView).setColor(
                    ColorUtils.computeGradientColor(
                    ContextCompat.getColor(recyclerView.context, R.color.grey),
                    ContextCompat.getColor(recyclerView.context, R.color.red),
                    (interpretateScale - scale) / (1 - scale)
                ))
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val layoutManager = recyclerView.layoutManager!!
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                val centerView = findCenterView(layoutManager, getHorizontalHelper(layoutManager))!!
                val centerIndex = layoutManager.getPosition(centerView)
                onCenter?.invoke(centerIndex)
            }
        }
    }

    private fun findCenterView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {
        val childCount = layoutManager.childCount
        if (childCount == 0) {
            return null
        }
        var closestChild: View? = null
        val center = helper.startAfterPadding + helper.totalSpace / 2
        var absClosest = Int.MAX_VALUE
        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            val childCenter = (helper.getDecoratedStart(child)
                    + helper.getDecoratedMeasurement(child) / 2)
            val absDistance = Math.abs(childCenter - center)
            /** if child center is closer than previous closest, set it as closest   */
            if (absDistance < absClosest) {
                absClosest = absDistance
                closestChild = child
            }
        }
        return closestChild
    }

}