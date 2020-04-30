package com.tainzhi.sample.api.widget;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.tainzhi.sample.api.adapter.FilterableAdapter;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-14 06:45
 * @description:
 **/

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
	
	private RecyclerItemTouchHelperListener mListener;
	
	public RecyclerItemTouchHelper(int dragDirs, int swipDirs,
	                               RecyclerItemTouchHelperListener listener) {
		super(dragDirs, swipDirs);
		this.mListener = listener;
	}
	
	@Override
	public int convertToAbsoluteDirection(int flags, int layoutDirection) {
		return super.convertToAbsoluteDirection(flags, layoutDirection);
	}
	
	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
		return true;
	}
	
	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
		mListener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
	}
	
	@Override
	public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
		if (viewHolder != null) {
			View foreground = ((FilterableAdapter.MyViewHolder) viewHolder).foreground;
			getDefaultUIUtil().onSelected(foreground);
		}
	}
	
	@Override
	public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
		View foreground = ((FilterableAdapter.MyViewHolder) viewHolder).foreground;
		getDefaultUIUtil().clearView(foreground);
	}
	
	@Override
	public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
		View foreground = ((FilterableAdapter.MyViewHolder) viewHolder).foreground;
		getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
	}
	
	@Override
	public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
		View foreground = ((FilterableAdapter.MyViewHolder) viewHolder).foreground;
		getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
	}
	
	public interface RecyclerItemTouchHelperListener {
		void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
	}
}
