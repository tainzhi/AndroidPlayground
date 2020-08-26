package com.tainzhi.sample.api.widget

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.adapter.FilterableAdapter

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-14 06:45
 * @description:
 */
class RecyclerItemTouchHelper(
    dragDirs: Int, swipDirs: Int,
    private val mListener: RecyclerItemTouchHelperListener
) : ItemTouchHelper.SimpleCallback(dragDirs, swipDirs) {
    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mListener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            val foreground = (viewHolder as FilterableAdapter.MyViewHolder).foreground
            getDefaultUIUtil().onSelected(foreground)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foreground = (viewHolder as FilterableAdapter.MyViewHolder).foreground
        getDefaultUIUtil().clearView(foreground)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foreground = (viewHolder as FilterableAdapter.MyViewHolder).foreground
        getDefaultUIUtil().onDraw(
            c,
            recyclerView,
            foreground,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foreground = (viewHolder as FilterableAdapter.MyViewHolder).foreground
        getDefaultUIUtil().onDrawOver(
            c,
            recyclerView,
            foreground,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}