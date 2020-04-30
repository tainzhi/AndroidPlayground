package com.tainzhi.sample.api.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-13 09:00
 * @description:
 **/

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {
	private static final int[] ATTRS = new int[]{
			android.R.attr.listDivider
	};
	
	private Drawable mDivider;
	private int mOrientation;
	private Context context;
	private int margin;
	
	public MyDividerItemDecoration(Context context, int orientation, int margin) {
		this.context = context;
		this.mOrientation = orientation;
		this.margin = margin;
		
		final TypedArray a = context.obtainStyledAttributes(ATTRS);
		mDivider = a.getDrawable(0);
		a.recycle();
		setOrientation(orientation);
	}
	
	public void setOrientation(int orientation) {
		if (orientation != LinearLayout.VERTICAL && orientation != LinearLayout.HORIZONTAL) {
			throw new IllegalArgumentException("invalid orientation!");
		}
		mOrientation = orientation;
	}
	
	@Override
	public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		if (mOrientation == LinearLayout.VERTICAL) {
			drawHorizontal(c, parent);
		} else {
			drawVertical(c, parent);
		}
	}
	
	@Override
	public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		if (mOrientation == LinearLayout.VERTICAL) {
			// 水平分割线高度
			outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
		} else {
			// 铅锤分割线宽度
			outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
		}
	}
	
	// 水平的分割线
	public void drawHorizontal(Canvas c, RecyclerView parent) {
		final int left = parent.getPaddingLeft();
		final int right = parent.getWidth() - parent.getPaddingRight();
		
		final int childCount = parent.getChildCount();
		
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int top = child.getBottom() + layoutParams.bottomMargin;
			final int bottom = top + mDivider.getIntrinsicHeight();
			mDivider.setBounds(left + dpToPx(margin), top, right - dpToPx(margin), bottom);
			mDivider.draw(c);
		}
	}
	
	// 铅锤的分割线
	public void drawVertical(Canvas c, RecyclerView parent) {
		final int top = parent.getPaddingTop();
		final int bottom = parent.getHeight() - parent.getPaddingBottom();
		
		final int childCount = parent.getChildCount();
		
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int left = child.getLeft() + layoutParams.leftMargin;
			final int right = left + mDivider.getIntrinsicWidth();
			mDivider.setBounds(left, top + dpToPx(margin), right, bottom - dpToPx(margin));
			mDivider.draw(c);
		}
	}
	
	private int dpToPx(int dp) {
		Resources r = context.getResources();
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
	}
}
