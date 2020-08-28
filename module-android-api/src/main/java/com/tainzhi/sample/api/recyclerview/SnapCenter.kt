package com.tainzhi.sample.api.recyclerview

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/8/26 17:24
 * @description:
 **/

class SnapCenter : LinearSnapHelper() {
    private var mVerticalHelper: OrientationHelper? = null
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

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToCenter(
                layoutManager, targetView,
                getVerticalHelper(layoutManager)
            )
        } else {
            out[1] = 0
        }
        return out    }

    private fun distanceToCenter(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View, helper: OrientationHelper
    ): Int {
        val targetPosition = layoutManager.getPosition(targetView)
        val childCenter = (helper.getDecoratedStart(targetView)
                + (if (targetPosition == 0) 0 else (+15))
                + helper.getDecoratedMeasurement(targetView) / 2)
        val containerCenter = helper.startAfterPadding + helper.totalSpace / 2
        return childCenter - containerCenter
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        return mVerticalHelper!!
    }

    private fun getHorizontalHelper(
        layoutManager: RecyclerView.LayoutManager
    ): OrientationHelper {
        mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        return mHorizontalHelper!!
    }


}