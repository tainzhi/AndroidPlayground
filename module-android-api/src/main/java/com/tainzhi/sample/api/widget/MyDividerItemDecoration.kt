package com.tainzhi.sample.api.widget

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-13 09:00
 * @description:
 */
class MyDividerItemDecoration(
    private val context: Context,
    private var mOrientation: Int,
    private val margin: Int
) : ItemDecoration() {
    private val mDivider: Drawable?
    fun setOrientation(orientation: Int) {
        require(!(orientation != LinearLayout.VERTICAL && orientation != LinearLayout.HORIZONTAL)) { "invalid orientation!" }
        mOrientation = orientation
    }

    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mOrientation == LinearLayout.VERTICAL) {
            drawHorizontal(c, parent)
        } else {
            drawVertical(c, parent)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (mOrientation == LinearLayout.VERTICAL) {
            // 水平分割线高度
            outRect[0, 0, 0] = mDivider!!.intrinsicHeight
        } else {
            // 铅锤分割线宽度
            outRect[0, 0, mDivider!!.intrinsicWidth] = 0
        }
    }

    // 水平的分割线
    fun drawHorizontal(c: Canvas?, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val layoutParams =
                child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + layoutParams.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left + dpToPx(margin), top, right - dpToPx(margin), bottom)
            mDivider.draw(c!!)
        }
    }

    // 铅锤的分割线
    fun drawVertical(c: Canvas?, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val layoutParams =
                child.layoutParams as RecyclerView.LayoutParams
            val left = child.left + layoutParams.leftMargin
            val right = left + mDivider!!.intrinsicWidth
            mDivider.setBounds(left, top + dpToPx(margin), right, bottom - dpToPx(margin))
            mDivider.draw(c!!)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val r = context.resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }

    companion object {
        private val ATTRS = intArrayOf(
            R.attr.listDivider
        )
    }

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(mOrientation)
    }
}