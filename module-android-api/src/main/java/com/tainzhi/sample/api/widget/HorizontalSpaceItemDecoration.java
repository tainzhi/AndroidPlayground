package com.tainzhi.sample.api.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by muqing on 2019-08-15.
 * Email: qfq61@qq.com
 */
public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
	private int space;
	
	public HorizontalSpaceItemDecoration(int space) { this.space = space; }
	
	;
	
	@Override
	public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		if (parent.getChildLayoutPosition(view) != 0) {
			outRect.left = space;
		}
	}
}
