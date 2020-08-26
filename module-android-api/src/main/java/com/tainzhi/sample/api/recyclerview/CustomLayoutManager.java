package com.tainzhi.sample.api.recyclerview;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2020/8/26 15:50
 * @description:
 **/

public class CustomLayoutManager extends LinearLayoutManager {
    private int mParentWidth;
    private int mItemWidth;
    
    public CustomLayoutManager(Context context, int parentWidth, int itemWidth) {
        super(context, RecyclerView.HORIZONTAL, false);
        mParentWidth = parentWidth;
        mItemWidth = itemWidth;
    }
    
    @Override
    public int getPaddingLeft() {
        return Math.round(mParentWidth / 2f - mItemWidth / 2f);
    }
    
    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }
}