package com.tainzhi.sample.api;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.tainzhi.sample.api.adapter.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-10 20:13
 * @description:
 **/

public class GuidePageActivity extends Activity implements ViewPager.OnPageChangeListener {
	private int[] imageIdArray;
	private List<View> imageViews;
	private List<ImageView> mPointViews;
	private ViewPager mViewPager;
	private LinearLayout mLlPointContainer;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_page);
		mViewPager = findViewById(R.id.view_pager);
		mLlPointContainer = findViewById(R.id.ll_guide_dot);
		initViewPager();
		initPoint();
	}
	
	private void initViewPager() {
		imageIdArray = new int[]{R.drawable.android_0, R.drawable.android_1, R.drawable.android_2,
				R.drawable.android_3, R.drawable.android_4, R.drawable.android_5};
		imageViews = new ArrayList<>();
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		for (int id : imageIdArray) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(id);
			imageView.setLayoutParams(params);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageViews.add(imageView);
		}
		mViewPager.setAdapter(new GuidePageAdapter(imageViews));
		mViewPager.addOnPageChangeListener(this);
	}
	
	private void initPoint() {
		mPointViews = new ArrayList<>();
		for (int i = 0, len = imageViews.size(); i < len; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
			params.setMargins(20, 0, 0, 0);
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(params);
			if (i == 0) {
				imageView.setImageResource(R.drawable.guide_page_selected_point);
			} else {
				imageView.setImageResource(R.drawable.guide_page_unselected_point);
			}
			mPointViews.add(imageView);
			mLlPointContainer.addView(imageView);
		}
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	
	}
	
	@Override
	public void onPageSelected(int position) {
		for (int i = 0, len = imageViews.size(); i < len; i++) {
			ImageView imageView = mPointViews.get(i);
			imageView.setImageResource(R.drawable.guide_page_unselected_point);
		}
		mPointViews.get(position).setImageResource(R.drawable.guide_page_selected_point);
	}
	
	@Override
	public void onPageScrollStateChanged(int state) {
	
	}
}
