package com.tainzhi.sample.api.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-11 09:03
 * @description:
 **/

public class GuidePageAdapter extends PagerAdapter {
	private List<View> mViewList;
	
	public GuidePageAdapter(List<View> data) {
		mViewList = data;
	}
	
	@Override
	public int getCount() {
		if (mViewList == null) {
			return 0;
		} else {
			return mViewList.size();
		}
	}
	
	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		container.addView(mViewList.get(position));
		return mViewList.get(position);
	}
	
	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView(mViewList.get(position));
	}
	
	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view == object;
	}
}
