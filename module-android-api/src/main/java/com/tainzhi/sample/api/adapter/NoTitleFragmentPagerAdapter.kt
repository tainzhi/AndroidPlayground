package com.tainzhi.sample.api.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by muqing on 9/1/2019.
 * Email: qfq61@qq.com
 */
public class NoTitleFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> mFragmentList;
	
	public NoTitleFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		mFragmentList = list;
	}
	
	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}
	
	@Override
	public int getCount() {
		return mFragmentList.size();
	}
}
