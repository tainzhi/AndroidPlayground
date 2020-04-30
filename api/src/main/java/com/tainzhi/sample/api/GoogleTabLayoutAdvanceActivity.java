package com.tainzhi.sample.api;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tainzhi.sample.api.adapter.NoTitleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoogleTabLayoutAdvanceActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_tab_layout_advance);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		initView();
	}
	
	private void initView() {
		TabLayout tabLayout = findViewById(R.id.tab_layout);
		ViewPager viewPager = findViewById(R.id.view_pager);
		String[] strings = {"fragment1", "fragment2", "fragment3", "fragment4", "fragment5"};
		List<Fragment> fragmentList = new ArrayList<>();
		for (int i = 0, length = strings.length; i < length; i++) {
			fragmentList.add(new MiniumFragment(strings[i]));
		}
		NoTitleFragmentPagerAdapter fragmentPagerAdapter =
				new NoTitleFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
		viewPager.setAdapter(fragmentPagerAdapter);
		// tabLayout.setupWithViewPager(viewPager); // 会覆盖掉xml文件的tabitem
		
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			
			}
		});
		
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				tabLayout.setScrollPosition(position, positionOffset, true);
			}
			
			@Override
			public void onPageSelected(int position) {
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			
			}
		});
	}
	
}
