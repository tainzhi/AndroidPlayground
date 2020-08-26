package com.tainzhi.sample.api.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by muqing on 2019-08-03.
 * Email: qfq61@qq.com
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> mFragmentList;
	private List<String> mTitleList;
	
	public SimpleFragmentPagerAdapter(FragmentManager fm,
	                                  List<String> titleList,
	                                  List<Fragment> fragmentList) {
		super(fm);
		mTitleList = titleList;
		mFragmentList = fragmentList;
	}
	
	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}
	
	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return mTitleList.get(position);
	}
	
	@Override
	public int getCount() {
		return mFragmentList.size();
	}
}
